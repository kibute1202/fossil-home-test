package com.sdt.fossilhometest.data.source.user;


import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.remote.api.UserApi;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;


public interface UserRepository {

    Single<List<User>> getUsersFromRemoteOrLocal(int page, int pageSize);

    UserApi getUserApi();

    UserDao getUserDao();

}
