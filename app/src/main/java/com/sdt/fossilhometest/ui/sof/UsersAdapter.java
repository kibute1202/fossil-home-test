package com.sdt.fossilhometest.ui.sof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ItemLoadUserErrorBinding;
import com.sdt.fossilhometest.databinding.ItemLoadingUserBinding;
import com.sdt.fossilhometest.databinding.ItemUserBinding;
import com.sdt.fossilhometest.ui.base.BaseAdapter;

public class UsersAdapter extends BaseAdapter<User, ViewDataBinding> {

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_ITEM = TYPE_LOADING + 1;
    private static final int TYPE_ERROR = TYPE_ITEM + 1;

    private NetworkState networkState;

    private OnRetryFetchUsersListener onRetryListener;

    public UsersAdapter() {
        super(new DiffUtil.ItemCallback<User>() {
            @Override
            public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.getUserId() == newItem.getUserId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @Override
    protected boolean enableStartAnimation() {
        return false;
    }

    @Override
    protected int getLayoutRes(int viewType) {
        if (viewType == TYPE_LOADING) {
            return R.layout.item_loading_user;
        } else if (viewType == TYPE_ERROR) {
            return R.layout.item_load_user_error;
        }
        return R.layout.item_user;
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            if (networkState.getStatus() == NetworkState.Status.FAILED) {
                return TYPE_ERROR;
            }
            return TYPE_LOADING;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new BaseViewHolder<>(
                ItemUserBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
                )
            );
        } else if (viewType == TYPE_ERROR) {
            return new BaseViewHolder<>(
                ItemLoadUserErrorBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
                )
            );
        }
        return new BaseViewHolder<>(
            ItemLoadingUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewDataBinding> holder, int position) {
        if (holder.getViewDataBinding() instanceof ItemLoadingUserBinding
            || holder.getViewDataBinding() instanceof ItemLoadUserErrorBinding) {
            bindView(holder.getViewDataBinding(), null, -1);
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    protected void bindView(ViewDataBinding viewDataBinding, User item, int position) {
        if (viewDataBinding instanceof ItemUserBinding) {
            bindUserView(((ItemUserBinding) viewDataBinding), item, position);
        } else {
            if (viewDataBinding instanceof ItemLoadingUserBinding)
                bindLoadMoreUserView(((ItemLoadingUserBinding) viewDataBinding));
            else if (viewDataBinding instanceof ItemLoadUserErrorBinding)
                bindLoadUserErrorView(((ItemLoadUserErrorBinding) viewDataBinding));
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    private void bindUserView(ItemUserBinding viewDataBinding, User item, int position) {
    }

    private void bindLoadMoreUserView(ItemLoadingUserBinding viewDataBinding) {
        if (networkState != null && networkState.getStatus() == NetworkState.Status.RUNNING) {
            viewDataBinding.shimmer.startShimmer();
        } else {
            viewDataBinding.shimmer.stopShimmer();
        }
    }

    private void bindLoadUserErrorView(ItemLoadUserErrorBinding viewDataBinding) {
        viewDataBinding.retry.setOnClickListener(v -> {
            if (onRetryListener != null) {
                onRetryListener.onRetry();
            }
        });
    }

    private boolean hasExtraRow() {
        return networkState != null && networkState != NetworkState.LOADED;
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void setOnRetryListener(OnRetryFetchUsersListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    public interface OnRetryFetchUsersListener {
        void onRetry();
    }

}
