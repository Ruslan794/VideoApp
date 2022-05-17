package com.example.videoapp.presentation.videoListScreen

import com.example.domain.models.Video

class VideoClickListener(val onItemClicked: (video: Video) -> Unit) {
    fun onClick(video: Video) = onItemClicked(video)
}