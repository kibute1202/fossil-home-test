package com.sdt.fossilhometest.data.source.user;

import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserRemoteDataSource implements UserDataSource {

    private UserApi api;

    @Inject
    public UserRemoteDataSource(UserApi api) {
        this.api = api;
    }

    @Override
    public Single<List<User>> getUsers(int page, int pageSize) {
        return api.getUsers(page, pageSize);
    }
}
