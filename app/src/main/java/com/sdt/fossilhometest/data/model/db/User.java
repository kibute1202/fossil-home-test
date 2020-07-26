package com.sdt.fossilhometest.data.model.db;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sdt.fossilhometest.utils.StringUtils;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("badge_counts")
    @Expose
    @Embedded
    private BadgeCounts badgeCounts;

    @SerializedName("account_id")
    @Expose
    private int accountId;

    @SerializedName("is_employee")
    @Expose
    private boolean isEmployee;

    @SerializedName("last_modified_date")
    @Expose
    private int lastModifiedDate;

    @SerializedName("last_access_date")
    @Expose
    private int lastAccessDate;

    @SerializedName("reputation_change_year")
    @Expose
    private int reputationChangeYear;

    @SerializedName("reputation_change_quarter")
    @Expose
    private int reputationChangeQuarter;

    @SerializedName("reputation_change_month")
    @Expose
    private int reputationChangeMonth;

    @SerializedName("reputation_change_week")
    @Expose
    private int reputationChangeWeek;

    @SerializedName("reputation_change_day")
    @Expose
    private int reputationChangeDay;

    @SerializedName("reputation")
    @Expose
    private int reputation;

    @SerializedName("creation_date")
    @Expose
    private int creationDate;

    @SerializedName("user_type")
    @Expose
    private String userType;

    @SerializedName("accept_rate")
    @Expose
    private int acceptRate;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("website_url")
    @Expose
    private String websiteUrl;

    @SerializedName("link")
    @Expose
    private String link;

    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    public User() {
    }

    public BadgeCounts getBadgeCounts() {
        if (badgeCounts == null) {
            badgeCounts = new BadgeCounts();
        }
        return badgeCounts;
    }

    public void setBadgeCounts(BadgeCounts badgeCounts) {
        this.badgeCounts = badgeCounts;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public int getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(int lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(int lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public int getReputationChangeYear() {
        return reputationChangeYear;
    }

    public void setReputationChangeYear(int reputationChangeYear) {
        this.reputationChangeYear = reputationChangeYear;
    }

    public int getReputationChangeQuarter() {
        return reputationChangeQuarter;
    }

    public void setReputationChangeQuarter(int reputationChangeQuarter) {
        this.reputationChangeQuarter = reputationChangeQuarter;
    }

    public int getReputationChangeMonth() {
        return reputationChangeMonth;
    }

    public void setReputationChangeMonth(int reputationChangeMonth) {
        this.reputationChangeMonth = reputationChangeMonth;
    }

    public int getReputationChangeWeek() {
        return reputationChangeWeek;
    }

    public void setReputationChangeWeek(int reputationChangeWeek) {
        this.reputationChangeWeek = reputationChangeWeek;
    }

    public int getReputationChangeDay() {
        return reputationChangeDay;
    }

    public void setReputationChangeDay(int reputationChangeDay) {
        this.reputationChangeDay = reputationChangeDay;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(int creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserType() {
        return StringUtils.safe(userType);
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(int acceptRate) {
        this.acceptRate = acceptRate;
    }

    public String getLocation() {
        return StringUtils.safe(location);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteUrl() {
        return StringUtils.safe(websiteUrl);
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLink() {
        return StringUtils.safe(link);
    }

    public void setLink(String link) {
        this.link = link;
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
        return getAccountId() == user.getAccountId() &&
            isEmployee() == user.isEmployee() &&
            getLastModifiedDate() == user.getLastModifiedDate() &&
            getLastAccessDate() == user.getLastAccessDate() &&
            getReputationChangeYear() == user.getReputationChangeYear() &&
            getReputationChangeQuarter() == user.getReputationChangeQuarter() &&
            getReputationChangeMonth() == user.getReputationChangeMonth() &&
            getReputationChangeWeek() == user.getReputationChangeWeek() &&
            getReputationChangeDay() == user.getReputationChangeDay() &&
            getReputation() == user.getReputation() &&
            getCreationDate() == user.getCreationDate() &&
            getUserId() == user.getUserId() &&
            getAcceptRate() == user.getAcceptRate() &&
            getBadgeCounts().equals(user.getBadgeCounts()) &&
            getUserType().equals(user.getUserType()) &&
            getLocation().equals(user.getLocation()) &&
            getWebsiteUrl().equals(user.getWebsiteUrl()) &&
            getLink().equals(user.getLink()) &&
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
            ", profileImage='" + profileImage + '\'' +
            ", displayName='" + displayName + '\'' +
            '}';
    }
}
