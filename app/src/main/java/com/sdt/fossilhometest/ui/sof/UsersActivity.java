package com.sdt.fossilhometest.ui.sof;

import android.os.Bundle;
import android.view.ViewStub;

import androidx.recyclerview.widget.DividerItemDecoration;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ActivityUsersBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;
import com.sdt.fossilhometest.utils.BindingUtils;

public class UsersActivity extends BaseActivity<ActivityUsersBinding, UsersViewModel> {

    private UsersAdapter usersAdapter;

    private ViewStub loadingViewStub;

    @Override
    protected int layoutResId() {
        return R.layout.activity_users;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        observeData();
    }

    private void setupUI() {
        loadingViewStub = viewDataBinding.loadingViewStub.getViewStub();
        if (loadingViewStub != null) {
            loadingViewStub.inflate();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.sof_users);
        }

        usersAdapter = new UsersAdapter();
        viewDataBinding.rvUsers.setAdapter(usersAdapter);
    }

    private void observeData() {
        viewModel.getPagedListUser().observe(this, users -> {
            usersAdapter.submitList(users);
        });

        viewModel.getNetworkState().observe(this, networkState -> {
            if (networkState == NetworkState.LOADED) {
                if (loadingViewStub != null) {
                    BindingUtils.gone(loadingViewStub);
                }
            }
        });
    }

}