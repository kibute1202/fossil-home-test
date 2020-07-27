package com.sdt.fossilhometest.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.sdt.fossilhometest.BR;
import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.ui.custom.LoadingDialog;
import com.sdt.fossilhometest.utils.DialogUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends DaggerAppCompatActivity {

    @Inject
    protected VM viewModel;

    protected V viewDataBinding;

    private Dialog alertDialog;
    private LoadingDialog loadingDialog;

    protected abstract
    @LayoutRes
    int layoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
        observe();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        hideAlert();
        hideLoading();
        super.onDestroy();
    }

    private void performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutResId());
        viewDataBinding.setVariable(BR.viewModel, viewModel);
        viewDataBinding.executePendingBindings();
    }

    private void observe() {

        viewModel.getOptionalErrorEvent().observe(this, this::handleOptionError);

    }

    protected void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    protected void hideLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
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
                this,
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

    protected void addFragment(@IdRes int containerId,
                               Fragment fragment,
                               String tag,
                               boolean addToBackStack,
                               int transition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(containerId, fragment, tag);
        commitTransaction(transaction, addToBackStack, transition);
    }

    protected void replaceFragment(@IdRes int containerId,
                                   Fragment fragment,
                                   String tag,
                                   boolean addToBackStack,
                                   int transition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment, tag);
        commitTransaction(transaction, addToBackStack, transition);
    }

    private void commitTransaction(FragmentTransaction transaction,
                                   boolean addToBackStack,
                                   int transition) {
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.setTransition(transition);
        transaction.commit();
    }

    protected void setTitleActionBar(@StringRes int res) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(res);
        }
    }

    protected void enableIconHome() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
