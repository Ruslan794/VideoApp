package com.example.domain.useCases

import com.example.domain.models.Video
import com.example.domain.repository.VideoRepository

class GetVideoListUseCase(private val repository: VideoRepository) {
    suspend fun execute(): List<Video>? = repository.getVideoList()
}