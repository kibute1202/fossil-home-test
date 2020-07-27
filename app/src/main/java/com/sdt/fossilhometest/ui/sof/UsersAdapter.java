package com.sdt.fossilhometest.ui.sof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ItemLoadUserErrorBinding;
import com.sdt.fossilhometest.databinding.ItemLoadingUserBinding;
import com.sdt.fossilhometest.databinding.ItemUserBinding;
import com.sdt.fossilhometest.ui.base.BaseAdapter;

import java.util.List;

public class UsersAdapter extends BaseAdapter<User, ViewDataBinding> {

    private static final int TYPE_LOADING = 1;
    private static final int TYPE_ITEM = TYPE_LOADING + 1;
    private static final int TYPE_ERROR = TYPE_ITEM + 1;

    private NetworkState networkState;

    private OnItemListener onItemListener;

    private ArrayMap<Integer, Boolean> bookmarks = new ArrayMap<>();

    public UsersAdapter() {
        super(User.ITEM_CALLBACK);
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
        setupBookmark(viewDataBinding, item);

        viewDataBinding.ivBookmark.setOnClickListener(v -> {
            if (onItemListener != null) {
                Boolean bookmarked = bookmarks.get(item.getUserId());
                if (bookmarked != null && bookmarked) {
                    onItemListener.unBookmark(item, position);
                    bookmarks.put(item.getUserId(), false);
                    updateBookmarkIcon(viewDataBinding, false);
                } else {
                    onItemListener.bookmark(item, position);
                    bookmarks.put(item.getUserId(), true);
                    updateBookmarkIcon(viewDataBinding, true);
                }
            }
        });

        viewDataBinding.rootView.setOnClickListener(v -> {
            if (onItemListener != null) {
                onItemListener.onClick(item);
            }
        });
    }

    private void setupBookmark(ItemUserBinding viewDataBinding, User item) {
        Boolean bookmarked = bookmarks.get(item.getUserId());
        if (bookmarked == null) {
            bookmarked = Boolean.FALSE;
            bookmarks.put(item.getUserId(), false);
        }
        updateBookmarkIcon(viewDataBinding, bookmarked);
    }

    private void updateBookmarkIcon(ItemUserBinding binding, boolean bookmarked) {
        binding.ivBookmark.setImageResource(
            bookmarked ? R.drawable.ic_star : R.drawable.ic_stroke_star);
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
            if (onItemListener != null) {
                onItemListener.onRetry();
            }
        });
    }

    private boolean hasExtraRow() {
        return networkState != null
            && networkState != NetworkState.LOADED
            && networkState != NetworkState.LOCAL;
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

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void updateBookmarkedUsers(List<Integer> userIds) {
        for (Integer userId : userIds) {
            if (!bookmarks.containsKey(userId)) {
                bookmarks.put(userId, true);
            }
        }
    }

    public interface OnItemListener {
        void onRetry();

        void onClick(User user);

        void bookmark(User user, int position);

        void unBookmark(User user, int position);
    }

}
