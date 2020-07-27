package com.sdt.fossilhometest.ui.sof.reputation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.ReputationHistory;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ItemLoadErrorBinding;
import com.sdt.fossilhometest.databinding.ItemLoadingReputationBinding;
import com.sdt.fossilhometest.databinding.ItemReputationHistoryBinding;
import com.sdt.fossilhometest.ui.base.BasePagedListAdapter;

public class ReputationAdapter extends BasePagedListAdapter<ReputationHistory, ViewDataBinding> {

    public ReputationAdapter() {
        super(ReputationHistory.ITEM_CALLBACK);
    }

    @Override
    protected boolean enableStartAnimation() {
        return super.enableStartAnimation();
    }

    @Override
    protected int getLayoutRes(int viewType) {
        if (viewType == TYPE_LOADING) {
            return R.layout.item_loading_reputation;
        } else if (viewType == TYPE_ERROR) {
            return R.layout.item_load_error;
        }
        return R.layout.item_reputation_history;
    }

    @NonNull
    @Override
    public BaseViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new BaseViewHolder<>(
                ItemReputationHistoryBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
                )
            );
        } else if (viewType == TYPE_ERROR) {
            return new BaseViewHolder<>(
                ItemLoadErrorBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
                )
            );
        }
        return new BaseViewHolder<>(
            ItemLoadingReputationBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
        if (holder.getViewDataBinding() instanceof ItemLoadingReputationBinding
            || holder.getViewDataBinding() instanceof ItemLoadErrorBinding) {
            bindView(holder.getViewDataBinding(), null, -1);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    protected void bindView(ViewDataBinding viewDataBinding, ReputationHistory item, int position) {
        if (viewDataBinding instanceof ItemReputationHistoryBinding) {
            bindItemView(((ItemReputationHistoryBinding) viewDataBinding), item, position);
        } else {
            if (viewDataBinding instanceof ItemLoadingReputationBinding)
                bindLoadingView(((ItemLoadingReputationBinding) viewDataBinding));
            else if (viewDataBinding instanceof ItemLoadErrorBinding)
                bindLoadErrorView(((ItemLoadErrorBinding) viewDataBinding));
        }
    }

    private void bindLoadErrorView(ItemLoadErrorBinding viewDataBinding) {
        viewDataBinding.retry.setOnClickListener(v -> {
//            if (onItemListener != null) {
//                onItemListener.onRetry();
//            }
        });
    }

    private void bindLoadingView(ItemLoadingReputationBinding viewDataBinding) {
        if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
            viewDataBinding.shimmer.startShimmer();
        } else {
            viewDataBinding.shimmer.stopShimmer();
        }
    }

    private void bindItemView(ItemReputationHistoryBinding viewDataBinding,
                              ReputationHistory item, int position) {

    }

}
