package com.sdt.fossilhometest.data.model.db;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdt.fossilhometest.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "users")
public class User {

    public static final DiffUtil.ItemCallback<User> ITEM_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getUserId() == newItem.getUserId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @PrimaryKey
    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("last_access_date")
    @Expose
    private int lastAccessDate;

    @SerializedName("reputation")
    @Expose
    private int reputation;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    public User() {
    }

    public int getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(int lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return StringUtils.safe(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileImage() {
        return StringUtils.safe(profileImage);
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return StringUtils.safe(displayName);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
            getLastAccessDate() == user.getLastAccessDate() &&
            getReputation() == user.getReputation() &&
            getLocation().equals(user.getLocation()) &&
            getProfileImage().equals(user.getProfileImage()) &&
            getDisplayName().equals(user.getDisplayName());
    }

    @Override
    public int hashCode() {
        return 31 + Integer.valueOf(userId).hashCode();
    }

    @NotNull
    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", lastAccessDate=" + lastAccessDate +
            ", reputation=" + reputation +
            ", location='" + location + '\'' +
            ", profileImage='" + profileImage + '\'' +
            ", displayName='" + displayName + '\'' +
            '}';
    }
}
