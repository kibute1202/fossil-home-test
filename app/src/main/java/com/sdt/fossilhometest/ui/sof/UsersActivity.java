package com.sdt.fossilhometest.ui.sof;

import android.os.Bundle;
import android.view.ViewStub;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ActivityUsersBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;
import com.sdt.fossilhometest.utils.BindingUtils;

public class UsersActivity extends BaseActivity<ActivityUsersBinding, UsersViewModel> {

    private UsersAdapter usersAdapter;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.sof_users);
        }

        usersAdapter = new UsersAdapter();
        usersAdapter.setOnRetryListener(() -> viewModel.retryFetchUsers());
        viewDataBinding.rvUsers.setAdapter(usersAdapter);

        viewDataBinding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getPagedListUser().observe(this, users -> {
            usersAdapter.submitList(users);
        });

        viewModel.getNetworkState().observe(this, networkState -> {
            usersAdapter.setNetworkState(networkState);
            if (networkState == NetworkState.LOADED) {
                viewDataBinding.swipeRefreshLayout.setRefreshing(false);
            } else if (networkState.getStatus() == NetworkState.Status.FAILED) {
                viewDataBinding.swipeRefreshLayout.setRefreshing(false);
                handleNetworkError(networkState.getCause());
            }
        });
    }

    private void handleNetworkError(Throwable cause) {
        if (usersAdapter.getItemCount() == 0) {

        } else {

        }
    }

}