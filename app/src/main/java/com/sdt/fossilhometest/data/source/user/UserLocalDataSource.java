package com.sdt.fossilhometest.data.source.user;

import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.AppDatabase;
import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.api.UserResponse;
import com.sdt.fossilhometest.data.model.db.User;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserLocalDataSource implements UserDataSource.Local {

    private final UserDao userDao;

    @Inject
    public UserLocalDataSource(AppDatabase appDatabase) {
        userDao = appDatabase.userDao();
    }

    @Override
    public DataSource.Factory<Integer, User> getUsers() {
        return userDao.getPagingUsers();
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }
}
