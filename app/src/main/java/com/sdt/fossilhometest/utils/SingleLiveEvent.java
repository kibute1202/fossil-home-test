package com.sdt.fossilhometest.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private AtomicBoolean pending = new AtomicBoolean(false);

    @Override
    public void setValue(T value) {
        pending.set(true);
        super.setValue(value);
    }

    @Override
    public void postValue(T value) {
        pending.set(true);
        super.postValue(value);
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, t -> {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t);
            }
        });
    }

    /**
     * Set null value on UI thread.
     */
    public void call() {
        setValue(null);
    }

    public void callOnBackground() {
        postValue(null);
    }
}
