package com.sdt.fossilhometest.data.source.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.AppDatabase;
import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import javax.inject.Inject;

public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<UserPageKeyedDataSource> sourceLiveData;
    private UserPageKeyedDataSource dataSource;

    private final UserApi userApi;
    private final UserDao userDao;

    @Inject
    public UserDataSourceFactory(UserApi userApi, UserDao userDao) {
        this.sourceLiveData = new MutableLiveData<>();
        this.userApi = userApi;
        this.userDao = userDao;
    }

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        dataSource = new UserPageKeyedDataSource(userApi);
        sourceLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<UserPageKeyedDataSource> getSourceLiveData() {
        return sourceLiveData;
    }

}
