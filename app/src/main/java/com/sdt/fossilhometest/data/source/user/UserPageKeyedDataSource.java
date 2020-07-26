package com.sdt.fossilhometest.data.source.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.api.UserResponse;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.remote.api.UserApi;
import com.sdt.fossilhometest.utils.Constants;
import com.sdt.fossilhometest.utils.ListUtils;
import com.sdt.fossilhometest.utils.ThreadUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class UserPageKeyedDataSource extends PageKeyedDataSource<Integer, User> {

    private static final String TAG = "UserPageKeyedDataSource";

    private final UserApi userApi;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initLoading;

    public UserPageKeyedDataSource(UserApi userApi) {
        this.userApi = userApi;
        initLoading = new MutableLiveData<>();
        networkState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, User> callback) {
        initLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        ThreadUtils.delay(Constants.LAZY_LOADING_TIME);

        userApi.getUsers(Constants.INITIAL_PAGE, Constants.PAGE_SIZE)
            .enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NotNull Call<UserResponse> call,
                                       @NotNull Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        List<User> result = response.body() == null
                            ? Collections.emptyList()
                            : ListUtils.safe(response.body().getUsers());
                        Timber.i("init items size = %d", result.size());

                        callback.onResult(result, null, Constants.INITIAL_PAGE + 1);

                        initLoading.postValue(NetworkState.LOADED);
                        networkState.postValue(NetworkState.LOADED);
                    } else {
                        initLoading.postValue(NetworkState.error(response.message()));
                        networkState.postValue(NetworkState.error(response.message()));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserResponse> call, @NotNull Throwable t) {
                    handleError(t);
                }
            });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params,
                           @NonNull LoadCallback<Integer, User> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params,
                          @NonNull LoadCallback<Integer, User> callback) {
        Timber.i("LoadUsers[page = %d, pageSize = %d]", params.key, params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);

        userApi.getUsers(params.key, Constants.PAGE_SIZE)
            .enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(@NotNull Call<UserResponse> call,
                                       @NotNull Response<UserResponse> response) {
                    if (response.isSuccessful()) {
                        UserResponse userResponse = response.body();
                        if (userResponse != null) {
                            Integer nextKey = userResponse.isHasMore() ? params.key + 1 : null;
                            Timber.i("items size = %d", ListUtils.safe(userResponse.getUsers()).size());
                            Timber.i("nextKey = %d", nextKey);
                            callback.onResult(ListUtils.safe(userResponse.getUsers()), nextKey);
                        }

                        networkState.postValue(NetworkState.LOADED);
                    } else {
                        initLoading.postValue(NetworkState.error(response.message()));
                        networkState.postValue(NetworkState.error(response.message()));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<UserResponse> call, @NotNull Throwable t) {
                    handleError(t);
                }
            });
    }

    private void handleError(Throwable t) {
        String errorMessage = t.getMessage();
        networkState.postValue(NetworkState.error(errorMessage));
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitLoading() {
        return initLoading;
    }
}
