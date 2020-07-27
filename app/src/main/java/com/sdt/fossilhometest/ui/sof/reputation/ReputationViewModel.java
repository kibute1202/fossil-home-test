package com.sdt.fossilhometest.ui.sof.reputation;

import androidx.lifecycle.LiveData;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ReputationViewModel extends BaseViewModel {

    private final UserRepository userRepository;

    private ExecutorService executor;
    private PagedList.Config pagedListConfig;
    private UserDataSourceFactory userDataSourceFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<User>> pagedListUser;

    @Inject
    public ReputationViewModel(SchedulerProvider schedulerProvider,
                               UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
        init();
    }

    private void init() {
        initialPagingReputations();
    }

    private void initialPagingReputations() {
        if (executor == null) {
            executor = Executors.newFixedThreadPool(Constants.DEFAULT_THREAD_POOL);
        }

        if (pagedListConfig == null) {
            pagedListConfig = (new PagedList.Config.Builder())
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Constants.PAGE_SIZE / 3)
                .setPageSize(Constants.PAGE_SIZE - 10)
                .build();
        }

        if (userDataSourceFactory == null) {
            userDataSourceFactory = new UserDataSourceFactory(
                false,
                userRepository.getUserApi(),
                userRepository.getUserDao(),
                executor,
                getCompositeDisposable()
            );
        }

        if (networkState == null) {
            networkState = Transformations.switchMap(
                userDataSourceFactory.getSourceLiveData(),
                UserPageKeyedDataSource::getNetworkState);
        }

        pagedListUser =
            (new LivePagedListBuilder<>(userDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public void refresh() {
        userDataSourceFactory.refresh();
    }

    public void retryFetchReputations() {
        userDataSourceFactory.doRetry();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<User>> getPagedListUser() {
        return pagedListUser;
    }
}
