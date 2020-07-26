package com.sdt.fossilhometest.ui.sof;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.databinding.ItemUserBinding;
import com.sdt.fossilhometest.ui.base.BaseAdapter;

public class UsersAdapter extends BaseAdapter<User, ItemUserBinding> {

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
        return R.layout.item_user;
    }

    @Override
    protected void bindView(ItemUserBinding viewDataBinding, User item, int position) {
    }

}
