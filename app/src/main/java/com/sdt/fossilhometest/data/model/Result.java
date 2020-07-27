package com.sdt.fossilhometest.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result<T> {

    @Expose
    @SerializedName("items")
    private List<T> items;

    @Expose
    @SerializedName("has_more")
    private boolean hasMore;

    public Result() {
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }
}
