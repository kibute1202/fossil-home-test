package com.sdt.fossilhometest.ui.sof.reputation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.sdt.fossilhometest.data.model.ReputationHistory;
import com.sdt.fossilhometest.data.model.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.data.source.reputation.ReputationDataSource;
import com.sdt.fossilhometest.data.source.reputation.ReputationDataSourceFactory;
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
    private ReputationDataSourceFactory reputationDataSourceFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<ReputationHistory>> pagedListReputationHistory;

    private MutableLiveData<User> user = new MutableLiveData<>();

    @Inject
    public ReputationViewModel(SchedulerProvider schedulerProvider,
                               UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
    }

    public void init(User user) {
        if (user != null) {
            this.user.setValue(user);
            initialPagingReputations(user);
        }
    }

    private void initialPagingReputations(User user) {
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

        if (reputationDataSourceFactory == null) {
            reputationDataSourceFactory = new ReputationDataSourceFactory(
                user.getUserId(),
                userRepository.getUserApi(),
                executor,
                getCompositeDisposable()
            );
        }

        if (networkState == null) {
            networkState = Transformations.switchMap(
                reputationDataSourceFactory.getSourceLiveData(),
                ReputationDataSource::getNetworkState);
        }

        pagedListReputationHistory =
            (new LivePagedListBuilder<>(reputationDataSourceFactory, pagedListConfig))
                .setFetchExecutor(executor)
                .build();
    }

    public void refresh() {
        reputationDataSourceFactory.refresh();
    }

    public void retryFetchReputations() {
        reputationDataSourceFactory.doRetry();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<ReputationHistory>> getPagedListReputationHistory() {
        return pagedListReputationHistory;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
