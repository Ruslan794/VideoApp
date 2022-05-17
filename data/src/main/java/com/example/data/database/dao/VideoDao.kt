package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.database.entities.VideoItem


@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    fun getAll(): List<VideoItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(videoItem: VideoItem): Long

}