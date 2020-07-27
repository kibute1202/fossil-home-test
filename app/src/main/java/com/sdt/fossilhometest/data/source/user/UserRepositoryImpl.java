package com.sdt.fossilhometest.data.source.user;


import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


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
    public Single<List<Integer>> getBookmarkedUserIds() {
        return local.getBookmarkedUserIds();
    }

    @Override
    public DataSource.Factory<Integer, User> getBookmarkedUsers() {
        return local.getBookmarkedUsers();
    }

    @Override
    public UserApi getUserApi() {
        return remote.getUserApi();
    }

    @Override
    public UserDao getUserDao() {
        return local.getUserDao();
    }

    @Override
    public void saveToBookmark(User user) {
        local.insertUser(user);
    }

    @Override
    public void removeFromBookmark(User user) {
        local.deleteUser(user);
    }

}
