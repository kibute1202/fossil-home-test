package com.sdt.fossilhometest.ui.sof;

import android.os.Bundle;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ActivityUsersBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;

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
        usersAdapter.setOnItemListener(onItemListener);
        viewDataBinding.rvUsers.setAdapter(usersAdapter);

        viewDataBinding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getPagedListUser().observe(this, usersAdapter::submitList);

        viewModel.getNetworkState().observe(this, this::handleNetworkState);
    }

    private void handleNetworkState(NetworkState networkState) {
        usersAdapter.setNetworkState(networkState);
        if (networkState == NetworkState.LOADED) {
            viewDataBinding.swipeRefreshLayout.setRefreshing(false);
        } else if (networkState.getStatus() == NetworkState.Status.FAILED) {
            viewDataBinding.swipeRefreshLayout.setRefreshing(false);
        }
    }

    private UsersAdapter.OnItemListener onItemListener = new UsersAdapter.OnItemListener() {
        @Override
        public void onRetry() {
            viewModel.retryFetchUsers();
        }

        @Override
        public void onClick(User user) {

        }
    };

}