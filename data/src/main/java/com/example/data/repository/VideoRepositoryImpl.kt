package com.example.data.repository

import com.example.data.api.VideoApi
import com.example.data.database.AppDatabaseHelper
import com.example.data.database.entities.VideoItem
import com.example.domain.models.Video
import com.example.domain.repository.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepositoryImpl(
    private val videoApi: VideoApi,
    private val mapper: DataClassMapper,
    private val database: AppDatabaseHelper
) :
    VideoRepository {

    private var cachedVideoList: List<Video>? = null
    private var lastSelectedVideo: Video? = null

    override suspend fun getVideoList(): List<Video>? {
        return when {
            (cachedVideoList != null) -> cachedVideoList

            (database.getAllVideos().isNotEmpty()) -> {
                val videoList = database.getAllVideos()
                cachedVideoList = mapper.videoItemListToVideoList(videoList)
                mapper.videoItemListToVideoList(videoList)
            }

            else -> {
                try {
                    val response = videoApi.getQuotes().body()
                    if (response != null) {
                        val result = mapper.videoApiResponseToVideoList(response)
                        cachedVideoList = result
                        withContext(Dispatchers.IO) {
                            saveVideoInfoToDatabase(
                                mapper.videoApiResponseToVideoItemList(response)
                            )
                        }
                        result
                    } else null
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    override fun changeLastSelectedVideo(newVideo: Video) {
        lastSelectedVideo = newVideo
    }

    override fun getLastSelectedVideo(): Video? = lastSelectedVideo

    private fun saveVideoInfoToDatabase(videos: List<VideoItem>) {
        videos.forEach {
            database.insertVideo(it)
        }
    }
}

