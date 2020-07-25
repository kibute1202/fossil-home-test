package com.sdt.fossilhometest.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.User;

@Database(entities = {
    User.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
}
