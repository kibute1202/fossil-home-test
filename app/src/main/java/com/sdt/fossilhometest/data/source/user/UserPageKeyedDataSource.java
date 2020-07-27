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
import java.util.concurrent.Executor;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class UserPageKeyedDataSource extends PageKeyedDataSource<Integer, User> {

    private static final String TAG = "UserPageKeyedDataSource";

    private final UserApi userApi;

    private MutableLiveData<NetworkState> networkState;
    private CompositeDisposable compositeDisposable;

    /**
     * Handle retry fetch data
     */
    private Executor retryExecutor;
    private boolean doRetryInitial = false;

    private LoadInitialParams<Integer> retryInitialParams;
    private LoadInitialCallback<Integer, User> retryInitialCallback;

    private LoadParams<Integer> retryParams;
    private LoadCallback<Integer, User> retryCallback;

    public UserPageKeyedDataSource(UserApi userApi,
                                   Executor retryExecutor,
                                   CompositeDisposable compositeDisposable) {
        this.userApi = userApi;
        this.retryExecutor = retryExecutor;
        this.compositeDisposable = compositeDisposable;

        networkState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params,
                            @NonNull LoadInitialCallback<Integer, User> callback) {

        networkState.postValue(NetworkState.LOADING);

        ThreadUtils.delay(Constants.LAZY_LOADING_TIME);

        Disposable disposable = userApi.getUsers(Constants.INITIAL_PAGE, Constants.PAGE_SIZE)
            .subscribe(userResponse -> {
                List<User> result = ListUtils.safe(userResponse.getUsers());
                callback.onResult(result, null, Constants.INITIAL_PAGE + 1);
                networkState.postValue(NetworkState.LOADED);
            }, throwable -> {
                retryInitialParams = params;
                retryInitialCallback = callback;
                doRetryInitial = true;
                handleError(throwable);
            });

        compositeDisposable.add(disposable);
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

        ThreadUtils.delay(Constants.LAZY_LOADING_TIME);

        Disposable disposable = userApi.getUsers(params.key, Constants.PAGE_SIZE)
            .subscribe(userResponse -> {
                Integer nextKey = userResponse.isHasMore() ? params.key + 1 : null;
                callback.onResult(ListUtils.safe(userResponse.getUsers()), nextKey);
                networkState.postValue(NetworkState.LOADED);
            }, throwable -> {
                doRetryInitial = false;
                retryParams = params;
                retryCallback = callback;
                handleError(throwable);
            });

        compositeDisposable.add(disposable);
    }

    public void doRetry() {
        retryExecutor.execute(() -> {
            if (doRetryInitial) {
                loadInitial(retryInitialParams, retryInitialCallback);
            } else {
                loadAfter(retryParams, retryCallback);
            }
        });
    }

    private void handleError(Throwable t) {
        networkState.postValue(NetworkState.error(t));
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
