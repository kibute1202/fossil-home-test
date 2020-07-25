package com.sdt.fossilhometest.data.local.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sdt.fossilhometest.data.model.db.RemoteKey;

import java.util.List;

@Dao
public interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RemoteKey> remoteKeys);

    @Query("SELECT * FROM remote_keys WHERE userId = :userId")
    RemoteKey getRemoteKeyByUserId(int userId);

    @Query("DELETE FROM remote_keys")
    void clearAll();

}
