package com.sdt.fossilhometest.data.source.user;


import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import io.reactivex.Single;


public interface UserDataSource {

    Single<List<User>> getUsers(int page, int pageSize);

}
