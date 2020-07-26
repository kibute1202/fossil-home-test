package com.sdt.fossilhometest.data.source.user;


import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;

import java.util.List;

import io.reactivex.Single;


public interface UserRepository {

    Single<List<User>> getUsersFromRemoteOrLocal(int page, int pageSize);

    UserDataSourceFactory getUserFactory();

    LiveData<PagedList<User>> getPagedUsers();

    LiveData<NetworkState> getNetworkState();

}
