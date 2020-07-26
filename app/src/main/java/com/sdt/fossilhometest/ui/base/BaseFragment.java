package com.sdt.fossilhometest.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.sdt.fossilhometest.BR;
import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.ui.custom.LoadingDialog;
import com.sdt.fossilhometest.utils.DialogUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public abstract class BaseFragment<V extends ViewDataBinding, VM extends BaseViewModel> extends DaggerFragment {

    protected BaseActivity activity;

    @Inject
    protected VM viewModel;

    protected V viewDataBinding;

    private Dialog alertDialog;
    private LoadingDialog loadingDialog;

    protected abstract
    @LayoutRes
    int layoutResId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.activity = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResId(), container, false);
        viewDataBinding.setVariable(BR.viewModel, viewModel);
        viewDataBinding.setLifecycleOwner(getViewLifecycleOwner());
        viewDataBinding.executePendingBindings();
        return viewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel.getOptionalErrorEvent().observe(getViewLifecycleOwner(), this::handleOptionError);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        hideAlert();
        hideLoading();
        super.onDestroyView();
    }

    private void handleOptionError(OptionalError optionalError) {
        switch (optionalError) {
            case UNKNOWN_ERROR:
                handleErrorMessage(getString(R.string.unknown_error));
                break;
            case SERVER_MAINTAIN:
                handleErrorMessage(getString(R.string.server_maintain_message));
                break;
            case FORCE_UPDATE_APP:
                handleErrorMessage(getString(R.string.force_update_app));
                break;
            case CONNECTION_TIME_OUT:
                handleErrorMessage(getString(R.string.connect_timeout));
                break;
            case NO_INTERNET_CONNECTION:
                handleErrorMessage(getString(R.string.no_internet_connection));
                break;
        }
    }

    private void handleErrorMessage(String error) {
        if (!TextUtils.isEmpty(error)) {
            hideAlert();
            alertDialog = DialogUtils.showDialog(
                getContext(),
                error,
                getString(R.string.ok)
            );
        }
    }

    protected void hideAlert() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    protected void showLoading() {
        if (getContext() == null) return;

        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    protected void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void addFragment(@IdRes int containerId,
                               Fragment fragment,
                               String tag,
                               boolean addToBackStack,
                               int transition) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(containerId, fragment, tag);
        commitTransaction(transaction, addToBackStack, transition);
    }

    protected void replaceFragment(@IdRes int containerId,
                                   Fragment fragment,
                                   String tag,
                                   boolean addToBackStack,
                                   int transition) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        commitTransaction(transaction, addToBackStack, transition);
    }

    private void commitTransaction(FragmentTransaction transaction,
                                   boolean addToBackStack,
                                   int transition) {
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.setTransition(transition);
    }

}
