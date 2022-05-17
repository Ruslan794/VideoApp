package com.example.data.database

import com.example.data.database.entities.VideoItem

class AppDatabaseHelper(private val db: AppDatabase) {

    fun getAllVideos(): List<VideoItem> = db.videoDao().getAll()

    fun insertVideo(videoItem: VideoItem): Long = db.videoDao().insert(videoItem = videoItem)
}