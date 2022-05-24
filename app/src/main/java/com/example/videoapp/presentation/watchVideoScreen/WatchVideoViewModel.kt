package com.example.videoapp.presentation.watchVideoScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.models.Video
import com.example.domain.useCases.GetVideoListUseCase
import com.example.domain.useCases.ShowSelectedVideoUseCase
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchVideoViewModel(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val showSelectedVideoUseCase: ShowSelectedVideoUseCase
) : ViewModel() {


    private val _videoList = MutableStateFlow<List<Video>>(emptyList())

    private val _currentVideo = MutableStateFlow<Video?>(null)
    val currentVideo: StateFlow<Video?> = _currentVideo.asStateFlow()

    init {
        viewModelScope.launch {
            showSelectedVideoUseCase.execute().let { if (it != null) _currentVideo.value = it }
            getVideoListUseCase.execute().let { if (it != null) _videoList.value = it }
        }
    }


    fun startCurrentVideo(player: ExoPlayer) {
        _videoList.value.forEach {
            player.addMediaItem(MediaItem.fromUri(it.videoSource))
        }
        player.prepare()
        while (player.currentMediaItem != MediaItem.fromUri(currentVideo.value!!.videoSource)) {
            player.seekToNext()
        }
        player.play()

    }

    fun onBackButtonClicked(navController: NavController, player: ExoPlayer) {
        player.stop()
        navController.popBackStack()
    }
}