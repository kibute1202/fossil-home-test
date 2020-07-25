package com.sdt.fossilhometest.data.source.user;


import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import io.reactivex.Single;


public interface UserRepository {

    Single<List<User>> getUsers(int page, int pageSize);

}
