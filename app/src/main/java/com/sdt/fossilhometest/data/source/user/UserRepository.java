package com.sdt.fossilhometest.data.source.user;


import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.List;

import io.reactivex.Single;


public interface UserRepository {

    Single<List<Integer>> getBookmarkedUserIds();

    DataSource.Factory<Integer, User> getBookmarkedUsers();

    UserApi getUserApi();

    UserDao getUserDao();

    void saveToBookmark(User user);

    void removeFromBookmark(User user);

}
