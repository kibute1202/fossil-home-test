package com.sdt.fossilhometest.ui.sof.reputation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.databinding.ActivityReputationHistoryBinding;
import com.sdt.fossilhometest.ui.base.BaseActivity;

public class ReputationHistoryActivity
    extends BaseActivity<ActivityReputationHistoryBinding, ReputationHistoryViewModel> {

    public static Intent newIntent(Activity from, User user) {
        Intent intent = new Intent(from, ReputationHistoryActivity.class);
        intent.putExtra(User.TAG, user);
        return intent;
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_reputation_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
        observeData();
    }

    private void setupUI() {

    }

    private void observeData() {

    }
}
