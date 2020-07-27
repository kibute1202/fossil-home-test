package com.sdt.fossilhometest.ui.sof;


import com.sdt.fossilhometest.R;
import com.sdt.fossilhometest.data.model.db.User;
import com.sdt.fossilhometest.databinding.ItemUserBinding;
import com.sdt.fossilhometest.ui.base.BaseAdapter;

public class UserLocalAdapter extends BaseAdapter<User, ItemUserBinding> {

    private OnItemListener onItemListener;

    public UserLocalAdapter() {
        super(User.ITEM_CALLBACK);
    }

    @Override
    protected int getLayoutRes(int viewType) {
        return R.layout.item_user;
    }

    @Override
    protected void bindView(ItemUserBinding viewDataBinding, User item, int position) {
        viewDataBinding.ivBookmark.setImageResource(R.drawable.ic_star);

        viewDataBinding.ivBookmark.setOnClickListener(v -> {
            if (onItemListener != null) {
                    onItemListener.onRemoveFromBookmark(item, position);
            }
        });

        viewDataBinding.rootView.setOnClickListener(v -> {
            if (onItemListener != null) {
                onItemListener.onClick(item);
            }
        });

    }

    public interface OnItemListener {
        void onClick(User user);

        void onRemoveFromBookmark(User user, int position);
    }
}
