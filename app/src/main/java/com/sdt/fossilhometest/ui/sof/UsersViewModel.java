package com.sdt.fossilhometest.ui.sof;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.source.user.UserRepository;
import com.sdt.fossilhometest.ui.base.BaseViewModel;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

public class UsersViewModel extends BaseViewModel {

    private UserRepository userRepository;

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<User>> pagedListUser;

    @Inject
    public UsersViewModel(SchedulerProvider schedulerProvider,
                          UserRepository userRepository) {
        super(schedulerProvider);
        networkState = userRepository.getNetworkState();
        pagedListUser = userRepository.getPagedUsers();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<User>> getPagedListUser() {
        return pagedListUser;
    }
}
