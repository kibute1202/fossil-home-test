package com.sdt.fossilhometest.ui.base;


import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.sdt.fossilhometest.BR;
import com.sdt.fossilhometest.R;

import java.util.List;

public abstract class BaseAdapter<T, V extends ViewDataBinding> extends ListAdapter<T, BaseAdapter.BaseViewHolder<V>> {

    protected BaseAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
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
//            holder.viewDataBinding.setVariable(BR.item, item);
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
//                holder.viewDataBinding.setVariable(BR.item, item);
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

    public static class BaseViewHolder<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        V viewDataBinding;

        public BaseViewHolder(@NonNull V viewDataBinding) {
            super(viewDataBinding.getRoot());
            this.viewDataBinding = viewDataBinding;
        }
    }
}
