package com.sdt.fossilhometest.data.source.user;

import com.sdt.fossilhometest.data.remote.api.UserApi;

import javax.inject.Inject;


public class UserRemoteDataSource implements UserDataSource.Remote {

    private UserApi api;

    @Inject
    public UserRemoteDataSource(UserApi api) {
        this.api = api;
    }

    @Override
    public UserApi getUserApi() {
        return api;
    }
}
