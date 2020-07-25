package com.sdt.fossilhometest.data.source.user;

import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class UserLocalDataSource implements UserDataSource {

    @Inject
    public UserLocalDataSource() {
    }

    @Override
    public Single<List<User>> getUsers() {
        return null;
    }

}
