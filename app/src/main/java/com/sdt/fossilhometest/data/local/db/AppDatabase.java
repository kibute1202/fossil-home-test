package com.sdt.fossilhometest.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sdt.fossilhometest.data.local.db.dao.RemoteKeyDao;
import com.sdt.fossilhometest.data.local.db.dao.UserDao;
import com.sdt.fossilhometest.data.model.db.RemoteKey;
import com.sdt.fossilhometest.data.model.db.User;

@Database(entities = {
    User.class,
    RemoteKey.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract RemoteKeyDao remoteKeyDao();


}
