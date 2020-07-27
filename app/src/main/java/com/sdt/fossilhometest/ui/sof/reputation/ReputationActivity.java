package com.sdt.fossilhometest.ui.sof.reputation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ActivityDetailReputationBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;
import com.sdt.fossilhometest.utils.BindingUtils;

public class ReputationActivity
    extends BaseActivity<ActivityDetailReputationBinding, ReputationViewModel> {

    public static final String VIEW_NAME_AVATAR_IMAGE = "detail:avatar:image";
    public static final String VIEW_NAME_DISPLAY_NAME_TITLE = "detail:displayName:title";
    public static final String VIEW_NAME_LOCATION_TITLE = "detail:location:title";
    public static final String VIEW_NAME_REPUTATION_TITLE = "detail:reputation:title";

    private ReputationAdapter reputationAdapter;

    public static Intent newIntent(Activity from, User user) {
        Intent intent = new Intent(from, ReputationActivity.class);
        intent.putExtra(User.TAG, user);
        return intent;
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_detail_reputation;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.init(getIntent().getParcelableExtra(User.TAG));
        setupUI();
        observeData();
    }

    private void setupUI() {
        setTitleActionBar(R.string.reputation_history);
        enableIconHome();

        ViewCompat.setTransitionName(viewDataBinding.ivAvatar, VIEW_NAME_AVATAR_IMAGE);
        ViewCompat.setTransitionName(viewDataBinding.tvDisplayName, VIEW_NAME_DISPLAY_NAME_TITLE);
        ViewCompat.setTransitionName(viewDataBinding.tvLocation, VIEW_NAME_LOCATION_TITLE);

        reputationAdapter = new ReputationAdapter();
        reputationAdapter.setOnItemListener(onItemListener);
        viewDataBinding.rvReputationHistory.setAdapter(reputationAdapter);

        ViewCompat.setNestedScrollingEnabled(viewDataBinding.rvReputationHistory, false);
    }

    private void observeData() {
        viewModel.getPagedListReputationHistory().observe(this, reputationAdapter::submitList);

        viewModel.getNetworkState().observe(this, this::handleNetworkState);

        viewModel.getUser().observe(this, this::setupUserInfo);
    }

    private void setupUserInfo(User user) {
        if (user != null) {
            BindingUtils.loadCircleImage(
                viewDataBinding.ivAvatar,
                user.getProfileImage(),
                ContextCompat.getDrawable(this, R.drawable.ic_default_user));
            viewDataBinding.tvDisplayName.setText(user.getDisplayName());
            viewDataBinding.tvLocation.setText(user.getLocation());
            BindingUtils.setTextWithNumber(viewDataBinding.tvReputation, user.getReputation());
        }
    }

    private void handleNetworkState(NetworkState networkState) {
        reputationAdapter.setNetworkState(networkState);
    }

    private ReputationAdapter.OnItemListener onItemListener = () -> viewModel.retryFetchReputations();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
