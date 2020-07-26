package com.sdt.fossilhometest.data.local.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sdt.fossilhometest.data.model.db.User;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("SELECT * FROM users")
    DataSource.Factory<Integer, User> getPagingUsers();

    @Query("SELECT * FROM users")
    LiveData<List<User>> getUsersLiveData();

    @Query("SELECT * FROM users")
    Single<List<User>> getUsers();

    @Query("SELECT * FROM users WHERE userId = :userId")
    User findUserById(int userId);

    @Query("SELECT * FROM users WHERE userId = :userId")
    Single<User> getUserById(int userId);

}
