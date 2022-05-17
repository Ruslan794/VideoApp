package com.example.domain.repository

import com.example.domain.models.Video

interface VideoRepository {

    suspend fun getVideoList(): List<Video>?

    fun changeLastSelectedVideo(newVideo: Video)

    fun getLastSelectedVideo(): Video?

}
