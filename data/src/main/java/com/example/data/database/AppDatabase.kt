package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.database.dao.VideoDao
import com.example.data.database.entities.VideoItem

@Database(
    entities = [VideoItem::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao
}