package com.sdt.fossilhometest.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;


public class UserResponse {

    @Expose
    @SerializedName("items")
    private List<User> users;

    @Expose
    @SerializedName("has_more")
    private boolean hasMore;

    public UserResponse() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

}
