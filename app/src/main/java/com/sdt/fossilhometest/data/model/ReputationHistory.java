package com.sdt.fossilhometest.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ReputationHistory {

    public static final DiffUtil.ItemCallback<ReputationHistory> ITEM_CALLBACK = new DiffUtil.ItemCallback<ReputationHistory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ReputationHistory oldItem, @NonNull ReputationHistory newItem) {
            return oldItem.getUserId() == newItem.getUserId() && oldItem.getPostId() == newItem.getPostId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReputationHistory oldItem, @NonNull ReputationHistory newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Expose
    @SerializedName("user_id")
    private int userId;

    @Expose
    @SerializedName("post_id")
    private int postId;

    @Expose
    @SerializedName("creation_date")
    private long creationDate;

    @Expose
    @SerializedName("reputation_change")
    private int reputationChange;

    @Expose
    @SerializedName("post_upvoted")
    private int postUpVoted;

    public ReputationHistory() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public int getReputationChange() {
        return reputationChange;
    }

    public void setReputationChange(int reputationChange) {
        this.reputationChange = reputationChange;
    }

    public int getPostUpVoted() {
        return postUpVoted;
    }

    public void setPostUpVoted(int postUpVoted) {
        this.postUpVoted = postUpVoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReputationHistory)) return false;
        ReputationHistory that = (ReputationHistory) o;
        return getUserId() == that.getUserId() &&
            getPostId() == that.getPostId() &&
            getCreationDate() == that.getCreationDate() &&
            getReputationChange() == that.getReputationChange() &&
            getPostUpVoted() == that.getPostUpVoted();
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + Integer.valueOf(userId).hashCode();
        hashCode = 31 * hashCode + Integer.valueOf(postId).hashCode();
        hashCode = 31 * hashCode + Long.valueOf(creationDate).hashCode();
        hashCode = 31 * hashCode + Long.valueOf(reputationChange).hashCode();
        hashCode = 31 * hashCode + Long.valueOf(postUpVoted).hashCode();
        return hashCode;
    }
}
