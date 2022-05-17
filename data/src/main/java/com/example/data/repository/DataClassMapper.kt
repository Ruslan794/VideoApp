package com.example.data.repository

import com.example.data.api.models.VideoApiResponse
import com.example.data.database.entities.VideoItem
import com.example.domain.models.Video

class DataClassMapper {

    fun videoApiResponseToVideoList(data: VideoApiResponse): List<Video> {
        return data.categories[0].videos.map {
            Video(
                it.title,
                it.description,
                it.thumb,
                it.sources[0]
            )
        }
    }

    fun videoItemListToVideoList(data: List<VideoItem>): List<Video> {
        return data.map {
            Video(
                it.name,
                it.description,
                it.thumbSource,
                it.videoSource
            )
        }
    }

    fun videoApiResponseToVideoItemList(data: VideoApiResponse): List<VideoItem> {
        return data.categories[0].videos.map {
            VideoItem(
                null,
                it.title,
                it.description,
                it.thumb,
                it.sources[0]
            )
        }
    }
}