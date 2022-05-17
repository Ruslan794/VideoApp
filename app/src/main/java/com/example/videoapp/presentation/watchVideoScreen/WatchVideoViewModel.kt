package com.example.videoapp.presentation.watchVideoScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.models.Video
import com.example.domain.useCases.GetVideoListUseCase
import com.example.domain.useCases.ShowSelectedVideoUseCase
import com.example.videoapp.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import kotlinx.coroutines.launch

class WatchVideoViewModel(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val showSelectedVideoUseCase: ShowSelectedVideoUseCase
) : ViewModel() {

    private val _videoList = MutableLiveData<List<Video>>()
    private val _currentVideo = MutableLiveData<Video>()
    val currentVideo: LiveData<Video> = _currentVideo

    init {
        viewModelScope.launch {
            showSelectedVideoUseCase.execute().let { if (it != null) _currentVideo.value = it }
            getVideoListUseCase.execute().let { if (it != null) _videoList.value = it }
        }
    }


    fun startCurrentVideo(player: ExoPlayer) {
        _videoList.value!!.forEach {
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
        navController.navigate(R.id.action_watchVideoFragment_to_videoListFragment)
    }
}