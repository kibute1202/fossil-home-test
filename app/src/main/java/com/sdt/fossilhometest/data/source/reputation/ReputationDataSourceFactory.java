package com.sdt.fossilhometest.data.source.reputation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;
import com.sdt.fossilhometest.data.source.user.UserPageKeyedDataSource;

import java.util.concurrent.Executor;

import io.reactivex.disposables.CompositeDisposable;

public class ReputationDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<ReputationDataSource> sourceLiveData;
    private ReputationDataSource dataSource;

    private final UserApi userApi;
    private final Executor executor;
    private final CompositeDisposable compositeDisposable;

    public ReputationDataSourceFactory(UserApi userApi,
                                       Executor executor,
                                       CompositeDisposable compositeDisposable) {
        this.userApi = userApi;
        this.executor = executor;
        this.compositeDisposable = compositeDisposable;
        this.sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        dataSource = new ReputationDataSource(userApi, executor, compositeDisposable);
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

    public MutableLiveData<ReputationDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

}
