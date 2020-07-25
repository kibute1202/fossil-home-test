package com.sdt.fossilhometest.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sdt.fossilhometest.data.remote.BaseException;
import com.sdt.fossilhometest.data.remote.ErrorHandlingFactory;
import com.sdt.fossilhometest.utils.SingleLiveEvent;
import com.sdt.fossilhometest.utils.rx.SchedulerProvider;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class BaseViewModel extends ViewModel {

    private final SchedulerProvider schedulerProvider;
    private final CompositeDisposable compositeDisposable;

    // optional error event
    private final MutableLiveData<OptionalError> optionalErrorEvent;

    // loading flag
    private final MutableLiveData<Boolean> isLoading;

    // error flags
    private final MutableLiveData<String> loadingErrorMessage;
    private final MutableLiveData<String> actionErrorMessage;

    // back flag
    private final SingleLiveEvent<Object> backEvent;

    public BaseViewModel(SchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();

        optionalErrorEvent = new MutableLiveData<>();

        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);

        loadingErrorMessage = new MutableLiveData<>();
        actionErrorMessage = new MutableLiveData<>();

        backEvent = new SingleLiveEvent<>();
    }

    protected void onLoadingError(Throwable throwable) {
        Timber.e(throwable);

        if (throwable instanceof UnknownHostException || throwable instanceof ConnectException) {
            optionalErrorEvent.setValue(OptionalError.NO_INTERNET_CONNECTION);
        } else if (throwable instanceof SocketTimeoutException) {
            optionalErrorEvent.setValue(OptionalError.CONNECTION_TIME_OUT);
        } else {
            BaseException baseException = ErrorHandlingFactory.convertToBaseException(throwable);
            switch (baseException.getHttpCode()) {
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    optionalErrorEvent.setValue(OptionalError.UNAUTHORIZED);
                    break;
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                    optionalErrorEvent.setValue(OptionalError.INTERNAL_ERROR);
                    break;
                default:
                    optionalErrorEvent.setValue(OptionalError.UNKNOWN_ERROR);
                    break;
            }
        }
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public MutableLiveData<OptionalError> getOptionalErrorEvent() {
        return optionalErrorEvent;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getLoadingErrorMessage() {
        return loadingErrorMessage;
    }

    public MutableLiveData<String> getActionErrorMessage() {
        return actionErrorMessage;
    }

    public SingleLiveEvent<Object> getBackEvent() {
        return backEvent;
    }

    protected Scheduler io() {
        return schedulerProvider.io();
    }

    protected Scheduler ui() {
        return schedulerProvider.ui();
    }

    protected Scheduler computation() {
        return schedulerProvider.computation();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
