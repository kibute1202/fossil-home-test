package com.sdt.fossilhometest.data.source.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.concurrent.Executor;

import io.reactivex.disposables.CompositeDisposable;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<UserPageKeyedDataSource> sourceLiveData;
    private UserPageKeyedDataSource dataSource;

    private boolean inLocal;
    private final UserApi userApi;
    private final UserDao userDao;
    private final Executor executor;
    private final CompositeDisposable compositeDisposable;

    public UserDataSourceFactory(boolean inLocal,
                                 UserApi userApi,
                                 UserDao userDao,
                                 Executor executor,
                                 CompositeDisposable compositeDisposable) {
        this.inLocal = inLocal;
        this.userApi = userApi;
        this.userDao = userDao;
        this.executor = executor;
        this.compositeDisposable = compositeDisposable;
        this.sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        dataSource = new UserPageKeyedDataSource(
            inLocal, userDao, userApi, executor, compositeDisposable);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public void switchSource(boolean isLocal) {
        if (this.inLocal != isLocal) {
            this.inLocal = isLocal;
            refresh();
        }
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

    public boolean isInLocal() {
        return inLocal;
    }

    public MutableLiveData<UserPageKeyedDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

}
