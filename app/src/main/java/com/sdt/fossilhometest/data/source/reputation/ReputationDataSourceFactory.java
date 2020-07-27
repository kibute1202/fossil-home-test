package com.sdt.fossilhometest.data.source.reputation;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.model.ReputationHistory;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.concurrent.Executor;

import io.reactivex.disposables.CompositeDisposable;

public class ReputationDataSourceFactory extends DataSource.Factory<Integer, ReputationHistory> {

    private MutableLiveData<ReputationDataSource> sourceLiveData;
    private ReputationDataSource dataSource;

    private final UserApi userApi;
    private final Integer userId;
    private final Executor executor;
    private final CompositeDisposable compositeDisposable;

    public ReputationDataSourceFactory(Integer userId,
                                       UserApi userApi,
                                       Executor executor,
                                       CompositeDisposable compositeDisposable) {
        this.userId = userId;
        this.userApi = userApi;
        this.executor = executor;
        this.compositeDisposable = compositeDisposable;
        this.sourceLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, ReputationHistory> create() {
        dataSource = new ReputationDataSource(userId, userApi, executor, compositeDisposable);
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
