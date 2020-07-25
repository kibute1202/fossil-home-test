package com.sdt.fossilhometest.data.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "remote_keys")
public class RemoteKey {

    @PrimaryKey
    private int userId;

    private int prevKey;

    private int nextKey;

    public RemoteKey() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrevKey() {
        return prevKey;
    }

    public void setPrevKey(int prevKey) {
        this.prevKey = prevKey;
    }

    public int getNextKey() {
        return nextKey;
    }

    public void setNextKey(int nextKey) {
        this.nextKey = nextKey;
    }
}
