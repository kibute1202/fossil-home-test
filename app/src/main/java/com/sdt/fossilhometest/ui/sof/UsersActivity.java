package com.sdt.fossilhometest.ui.sof;

import android.os.Bundle;

import com.sdt.fossilhometest.R;
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
        usersAdapter = new UsersAdapter();
        viewDataBinding.rvUsers.setAdapter(usersAdapter);
    }

    private void observeData() {
        viewModel.getPagedListUser().observe(this, usersAdapter::submitList);
    }

}