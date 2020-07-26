package com.sdt.fossilhometest.data.source.user;


import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.api.UserApi;


public interface UserDataSource {

    interface Local {

        UserDao getUserDao();

        DataSource.Factory<Integer, User> getUsers();

    }

    interface Remote {

        UserApi getUserApi();

    }

}
