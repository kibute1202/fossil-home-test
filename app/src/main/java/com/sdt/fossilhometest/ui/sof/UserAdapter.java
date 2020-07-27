package com.sdt.fossilhometest.ui.sof;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;
import androidx.databinding.ViewDataBinding;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.data.remote.NetworkState;
import com.sdt.fossilhometest.databinding.ItemLoadErrorBinding;
import com.sdt.fossilhometest.databinding.ItemLoadingUserBinding;
import com.sdt.fossilhometest.databinding.ItemUserBinding;
import com.sdt.fossilhometest.ui.base.BasePagedListAdapter;

import java.util.List;

public class UserAdapter extends BasePagedListAdapter<User, ViewDataBinding> {

    private OnItemListener onItemListener;

    private ArrayMap<Integer, Boolean> bookmarks = new ArrayMap<>();

    public UserAdapter() {
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
            return R.layout.item_load_error;
        }
        return R.layout.item_user;
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
                ItemLoadErrorBinding.inflate(
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
            || holder.getViewDataBinding() instanceof ItemLoadErrorBinding) {
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
            else if (viewDataBinding instanceof ItemLoadErrorBinding)
                bindLoadUserErrorView(((ItemLoadErrorBinding) viewDataBinding));
        }
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

    private void bindLoadUserErrorView(ItemLoadErrorBinding viewDataBinding) {
        viewDataBinding.retry.setOnClickListener(v -> {
            if (onItemListener != null) {
                onItemListener.onRetry();
            }
        });
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
