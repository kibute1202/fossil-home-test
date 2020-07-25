package com.sdt.fossilhometest.data.model.db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BadgeCounts {
    @SerializedName("bronze")
    @Expose
    private int bronze;

    @SerializedName("silver")
    @Expose
    private int silver;

    @SerializedName("gold")
    @Expose
    private int gold;

    public int getBronze() {
        return bronze;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BadgeCounts)) return false;
        BadgeCounts that = (BadgeCounts) o;
        return getBronze() == that.getBronze() &&
            getSilver() == that.getSilver() &&
            getGold() == that.getGold();
    }

    @Override
    public int hashCode() {
        return 31
            + Integer.valueOf(bronze).hashCode()
            + Integer.valueOf(silver).hashCode()
            + Integer.valueOf(gold).hashCode();
    }
}
