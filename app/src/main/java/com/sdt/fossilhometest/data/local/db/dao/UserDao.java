package com.sdt.fossilhometest.data.local.db.dao;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sdt.fossilhometest.data.model.User;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("SELECT userId FROM users")
    Single<List<Integer>> getBookmarkedUserIds();

    @Query("SELECT * FROM users")
    DataSource.Factory<Integer, User> getBookmarkedUsers();

    @Query("SELECT * FROM users LIMIT :limit OFFSET :offset")
    Single<List<User>> getBookmarkedUsers(int limit, int offset);

}
