package com.sdt.fossilhometest.data.source.user;


import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;


public class UserRepositoryImpl implements UserRepository {

    private final UserDataSource local;
    private final UserDataSource remote;

    @Inject
    public UserRepositoryImpl(UserLocalDataSource local,
                              UserRemoteDataSource remote) {
        this.local = local;
        this.remote = remote;
    }

    @Override
    public Single<List<User>> getUsers(int page, int pageSize) {
        return remote.getUsers(page, pageSize);
    }
}
