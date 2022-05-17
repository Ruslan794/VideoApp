package com.example.domain.useCases

import com.example.domain.models.Video
import com.example.domain.repository.VideoRepository

class ShowSelectedVideoUseCase(private val repository: VideoRepository) {
     fun execute(): Video? = repository.getLastSelectedVideo()
}