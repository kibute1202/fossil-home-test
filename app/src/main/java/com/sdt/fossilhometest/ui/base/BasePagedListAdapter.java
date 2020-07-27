package com.sdt.fossilhometest.ui.base;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.sdt.fossilhometest.BR;
import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.remote.NetworkState;

import java.util.List;

public abstract class BasePagedListAdapter<T, V extends ViewDataBinding> extends PagedListAdapter<T, BasePagedListAdapter.BaseViewHolder<V>> {

    protected static final int TYPE_LOADING = 1;
    protected static final int TYPE_ITEM = TYPE_LOADING + 1;
    protected static final int TYPE_ERROR = TYPE_ITEM + 1;

    protected NetworkState networkState;

    protected BasePagedListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
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

    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasExtraRow() ? 1 : 0);
    }

    @NonNull
    @Override
    public BaseViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder<>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getLayoutRes(viewType),
                parent,
                false
            )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<V> holder, int position) {
        T item = getItem(position);
        if (item != null) {
            holder.viewDataBinding.setVariable(BR.item, item);
            bindView(holder.viewDataBinding, item, position);
            holder.viewDataBinding.executePendingBindings();
            setupAnimation(holder);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<V> holder, int position,
                                 @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            T item = getItem(position);
            if (item != null) {
                holder.viewDataBinding.setVariable(BR.item, item);
                bindView(holder.viewDataBinding, item, position, payloads);
                holder.viewDataBinding.executePendingBindings();
                setupAnimation(holder);
            }
        } else
            onBindViewHolder(holder, position);
    }

    protected abstract @LayoutRes
    int getLayoutRes(int viewType);

    protected boolean enableStartAnimation() {
        return true;
    }

    protected void bindView(V viewDataBinding, T item, int position) {
    }

    protected void bindView(V viewDataBinding, T item, int position, List<Object> payloads) {
    }

    private void setupAnimation(BaseViewHolder<V> holder) {
        if (enableStartAnimation()) {
            holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(
                    holder.itemView.getContext(),
                    R.anim.fade_in
                )
            );
        }
    }

    protected boolean hasExtraRow() {
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

    public Context context(ViewDataBinding binding) {
        return binding.getRoot().getContext();
    }

    public String stringRes(ViewDataBinding binding, @StringRes int res) {
        return context(binding).getString(res);
    }

    public static class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        protected V viewDataBinding;

        public BaseViewHolder(@NonNull V viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }

        public V getViewDataBinding() {
            return viewDataBinding;
        }
    }
}
