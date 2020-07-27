package com.sdt.fossilhometest.data.source.user;

import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.AppDatabase;
import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserLocalDataSource implements UserDataSource.Local {

    private final UserDao userDao;

    @Inject
    public UserLocalDataSource(AppDatabase appDatabase) {
        userDao = appDatabase.userDao();
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public Single<List<Integer>> getBookmarkedUserIds() {
        return userDao.getBookmarkedUserIds();
    }

    @Override
    public DataSource.Factory<Integer, User> getBookmarkedUsers() {
        return userDao.getBookmarkedUsers();
    }

    @Override
    public void insertUser(User user) {
        userDao.insert(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
