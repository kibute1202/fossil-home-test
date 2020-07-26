package com.sdt.fossilhometest.data.source.user;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.model.api.UserResponse;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.utils.Constants;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Single;


public class UserRepositoryImpl implements UserRepository {

    private final SchedulerProvider schedulerProvider;

    private final UserDataSource.Local local;
    private final UserDataSource.Remote remote;

    private Executor executor;
    private PagedList.Config pagedListConfig;
    private UserDataSourceFactory userDataSourceFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<User>> userPagedListLiveData;

    @Inject
    public UserRepositoryImpl(SchedulerProvider schedulerProvider,
                              UserLocalDataSource local,
                              UserRemoteDataSource remote) {
        this.schedulerProvider = schedulerProvider;
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Single<List<User>> getUsersFromRemoteOrLocal(int page, int pageSize) {
        return null;
    }

    @Override
    public UserDataSourceFactory getUserFactory() {
        if (userDataSourceFactory == null) {
            userDataSourceFactory = new UserDataSourceFactory(remote.getUserApi(), local.getUserDao());
        }
        return userDataSourceFactory;
    }

    @Override
    public LiveData<PagedList<User>> getPagedUsers() {
        UserDataSourceFactory factory = getUserFactory();
        if (executor == null) {
            executor = Executors.newFixedThreadPool(5);
        }
        if (pagedListConfig == null) {
            pagedListConfig =
                (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(Constants.PAGE_SIZE / 2)
                    .setPageSize(Constants.PAGE_SIZE)
                    .build();
        }
        if (userPagedListLiveData == null) {
            userPagedListLiveData =
                (new LivePagedListBuilder<>(factory, pagedListConfig))
                    .setFetchExecutor(executor)
                    .build();
        }
        return userPagedListLiveData;
    }

    @Override
    public LiveData<NetworkState> getNetworkState() {
        if (networkState == null) {
            networkState = Transformations.switchMap(
                getUserFactory().getSourceLiveData(),
                UserPageKeyedDataSource::getNetworkState);
        }
        return networkState;
    }

}
