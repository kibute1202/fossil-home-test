package com.sdt.fossilhometest.data.source.user;


import androidx.paging.DataSource;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.List;

import io.reactivex.Single;


public interface UserDataSource {

    interface Local {

        UserDao getUserDao();

        Single<List<Integer>> getBookmarkedUserIds();

        DataSource.Factory<Integer, User> getBookmarkedUsers();

        void insertUser(User user);

        void deleteUser(User user);
    }

    interface Remote {

        UserApi getUserApi();

    }

}
