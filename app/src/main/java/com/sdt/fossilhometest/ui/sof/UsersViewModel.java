package com.sdt.fossilhometest.ui.sof;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.DataSource;
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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class UsersViewModel extends BaseViewModel {

    private UserRepository userRepository;

    private ExecutorService executor;
    private PagedList.Config pagedListConfig;
    private UserDataSourceFactory userDataSourceFactory;
    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<User>> pagedListUser;

    private MutableLiveData<List<Integer>> bookmarkedUserIds;

    private Filter filterUser;

    @Inject
    public UsersViewModel(SchedulerProvider schedulerProvider,
                          UserRepository userRepository) {
        super(schedulerProvider);
        this.userRepository = userRepository;
        init();
    }

    private void init() {
        filterUser = Filter.ALL;
        initialBookmarkedUserIds();
        initialPagingUsers();
    }

    private void initialBookmarkedUserIds() {
        bookmarkedUserIds = new MutableLiveData<>();

        Disposable bookmarksDisposable = userRepository.getBookmarkedUserIds()
            .subscribeOn(io())
            .observeOn(ui())
            .subscribe(userIds -> {
                bookmarkedUserIds.setValue(userIds);
            }, Timber::e);

        addDisposable(bookmarksDisposable);
    }

    private void initialPagingUsers() {
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

    public void saveToBookmarks(User user, BaseActionListener listener) {
        Disposable disposable = Observable.just(user)
            .subscribeOn(io())
            .map(item -> {
                userRepository.saveToBookmark(item);
                return true;
            })
            .observeOn(ui())
            .subscribe(any -> {
                listener.onSuccess();
            }, throwable -> {
                Timber.e(throwable);
                listener.onFailed();
            });
        addDisposable(disposable);
    }

    public void removeFromBookmarks(User user, BaseActionListener listener) {
        Disposable disposable = Observable.just(user)
            .subscribeOn(io())
            .map(item -> {
                userRepository.removeFromBookmark(item);
                return true;
            })
            .observeOn(ui())
            .subscribe(any -> {
                if (userDataSourceFactory.isInLocal()) {
                    refresh();
                }
                listener.onSuccess();
            }, throwable -> {
                Timber.e(throwable);
                listener.onFailed();
            });
        addDisposable(disposable);
    }

    public void refresh() {
        userDataSourceFactory.refresh();
    }

    public void retryFetchUsers() {
        userDataSourceFactory.doRetry();
    }

    public void filterAllUsers() {
        if (filterUser != Filter.ALL) {
            filterUser = Filter.ALL;
            userDataSourceFactory.switchSource(false);
        }
    }

    public void filterBookmarkedUsers() {
        if (filterUser != Filter.BOOKMARK) {
            filterUser = Filter.BOOKMARK;
            userDataSourceFactory.switchSource(true);
        }
    }

    public MutableLiveData<List<Integer>> getBookmarkedUserIds() {
        return bookmarkedUserIds;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<User>> getPagedListUser() {
        return pagedListUser;
    }

    public Filter getFilterUser() {
        return filterUser;
    }

    public enum Filter {
        ALL,
        BOOKMARK
    }

    public interface BaseActionListener {
        void onSuccess();

        void onFailed();
    }

}
