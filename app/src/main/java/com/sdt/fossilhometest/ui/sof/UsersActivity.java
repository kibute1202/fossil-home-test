package com.sdt.fossilhometest.ui.sof;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ActivityUsersBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;
import com.sdt.fossilhometest.ui.sof.reputation.ReputationHistoryActivity;

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
        setTitleActionBar(R.string.sof_users);

        usersAdapter = new UsersAdapter();
        usersAdapter.setOnItemListener(onItemListener);
        viewDataBinding.rvUsers.setAdapter(usersAdapter);

        viewDataBinding.swipeRefreshLayout.setOnRefreshListener(() -> viewModel.refresh());
    }

    private void observeData() {
        viewModel.getBookmarkedUserIds().observe(this, usersAdapter::updateBookmarkedUsers);

        viewModel.getPagedListUser().observe(this, usersAdapter::submitList);

        viewModel.getNetworkState().observe(this, this::handleNetworkState);
    }

    private void handleNetworkState(NetworkState networkState) {
        usersAdapter.setNetworkState(networkState);
        if (networkState == NetworkState.LOADED) {
            viewDataBinding.swipeRefreshLayout.setRefreshing(false);
        } else if (networkState.getStatus() == NetworkState.Status.FAILED
            || networkState == NetworkState.LOCAL) {
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
            startActivity(ReputationHistoryActivity.newIntent(UsersActivity.this, user));
        }

        @Override
        public void bookmark(User user, int position) {
            handleSaveToBookmarks(user, position);
        }

        @Override
        public void unBookmark(User user, int position) {
            handleRemoveFromBookmarks(user, position);
        }
    };

    private void handleRemoveFromBookmarks(User user, int position) {
        viewModel.removeFromBookmarks(user, new UsersViewModel.BaseActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UsersActivity.this,
                    R.string.remove_from_bookmark, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(UsersActivity.this,
                    R.string.cannot_remove_from_bookmark, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSaveToBookmarks(User user, int position) {
        viewModel.saveToBookmarks(user, new UsersViewModel.BaseActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(UsersActivity.this,
                    R.string.saved_to_bookmark, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(UsersActivity.this,
                    R.string.cannot_saved_to_bookmark, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_users_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allUsers:
                item.setChecked(true);
                setTitleActionBar(R.string.sof_users);
                filterAllUsers();
                break;
            case R.id.bookmark:
                item.setChecked(true);
                setTitleActionBar(R.string.bookmark);
                filterBookmarkedUsers();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterBookmarkedUsers() {
        if (viewModel.getFilterUser() != UsersViewModel.Filter.BOOKMARK) {
            usersAdapter.submitList(null);
            viewModel.filterBookmarkedUsers();
        }
    }

    private void filterAllUsers() {
        if (viewModel.getFilterUser() != UsersViewModel.Filter.ALL) {
            usersAdapter.submitList(null);
            viewModel.filterAllUsers();
        }
    }
}