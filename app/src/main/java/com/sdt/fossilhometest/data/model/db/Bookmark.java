package com.sdt.fossilhometest.data.model.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class Bookmark {

    @PrimaryKey
    private int userId;

}
