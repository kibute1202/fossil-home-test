package com.sdt.fossilhometest.data.source.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.AppDatabase;
import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<UserPageKeyedDataSource> sourceLiveData;
    private UserPageKeyedDataSource dataSource;

    private final UserApi userApi;
    private final UserDao userDao;
    private final Executor executor;
    private final CompositeDisposable compositeDisposable;

    public UserDataSourceFactory(UserApi userApi, UserDao userDao,
                                 Executor executor,
                                 CompositeDisposable compositeDisposable) {
        this.userApi = userApi;
        this.userDao = userDao;
        this.executor = executor;
        this.compositeDisposable = compositeDisposable;
        this.sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        dataSource = new UserPageKeyedDataSource(userApi, executor, compositeDisposable);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public void refresh() {
        if (dataSource != null) {
            dataSource.invalidate();
        }
    }

    public void doRetry() {
        if (dataSource != null) {
            dataSource.doRetry();
        }
    }

    public MutableLiveData<UserPageKeyedDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

}
