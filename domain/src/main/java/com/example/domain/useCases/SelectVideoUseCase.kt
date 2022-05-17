package com.example.domain.useCases

import com.example.domain.models.Video
import com.example.domain.repository.VideoRepository

class SelectVideoUseCase(private val repository: VideoRepository) {
    fun execute(video: Video) = repository.changeLastSelectedVideo(newVideo = video)
}