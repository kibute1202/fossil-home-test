package com.sdt.fossilhometest.ui.sof;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.source.user.UserDataSourceFactory;
import com.sdt.fossilhometest.data.source.user.UserPageKeyedDataSource;
import com.sdt.fossilhometest.data.source.user.UserRepository;
import com.sdt.fossilhometest.ui.base.BaseViewModel;
import com.sdt.fossilhometest.utils.Constants;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class UsersViewModel extends BaseViewModel {

    private UserRepository userRepository;

    private UserDataSourceFactory userDataSourceFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<User>> pagedListUser;

    private MutableLiveData<Filter> filterLiveData;

    @Inject
    public UsersViewModel(SchedulerProvider schedulerProvider,
                          UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
        init();
    }

    private void init() {
        filterLiveData = new MutableLiveData<>();
        filterLiveData.setValue(Filter.ALL);

        Executor executor = Executors.newFixedThreadPool(Constants.DEFAULT_THREAD_POOL);

        PagedList.Config pagedListConfig = (new PagedList.Config.Builder())
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(Constants.PAGE_SIZE / 3)
            .setPageSize(Constants.PAGE_SIZE - 10)
            .build();

        userDataSourceFactory = new UserDataSourceFactory(
            userRepository.getUserApi(),
            userRepository.getUserDao(),
            executor,
            getCompositeDisposable()
        );

        networkState = Transformations.switchMap(
            userDataSourceFactory.getSourceLiveData(),
            UserPageKeyedDataSource::getNetworkState);

        pagedListUser =
            (new LivePagedListBuilder<>(userDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public void bookmark(User user) {

    }

    public void unBookmark(User user) {

    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<User>> getPagedListUser() {
        return pagedListUser;
    }

    public void refresh() {
        userDataSourceFactory.refresh();
    }

    public void retryFetchUsers() {
        userDataSourceFactory.doRetry();
    }

    public MutableLiveData<Filter> getFilterLiveData() {
        return filterLiveData;
    }

    public enum  Filter {
        ALL,
        BOOKMARK
    }

}
