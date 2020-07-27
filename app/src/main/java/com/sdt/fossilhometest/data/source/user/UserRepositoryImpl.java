package com.sdt.fossilhometest.data.source.user;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.api.UserResponse;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.remote.api.UserApi;
import com.sdt.fossilhometest.utils.Constants;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public class UserRepositoryImpl implements UserRepository {

    private final SchedulerProvider schedulerProvider;

    private final UserDataSource.Local local;
    private final UserDataSource.Remote remote;

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
    public UserApi getUserApi() {
        return remote.getUserApi();
    }

    @Override
    public UserDao getUserDao() {
        return local.getUserDao();
    }

}
