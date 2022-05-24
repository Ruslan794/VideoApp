package com.example.videoapp.presentation.videoListScreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.models.Video
import com.example.domain.useCases.GetVideoListUseCase
import com.example.domain.useCases.SelectVideoUseCase
import com.example.videoapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoListViewModel(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val selectVideoUseCase: SelectVideoUseCase
) : ViewModel() {

    private val _videoList = MutableStateFlow<List<Video>>(emptyList())
    val videoList: StateFlow<List<Video>> = _videoList.asStateFlow()

    private val userBehaviorChannel = Channel<UserBehaviorEvents>()
    val userBehaviorFlow = userBehaviorChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            getVideoList()
        }
    }

    fun triggerUserInactionBehavior() = viewModelScope.launch {
        userBehaviorChannel.send(UserBehaviorEvents.Inaction("You just sit here for about 10 seconds."))
        userBehaviorChannel.send(UserBehaviorEvents.Inaction("Is it so hard to choose?"))
    }

    private fun triggerUserVideoChosenBehavior() = viewModelScope.launch {
        userBehaviorChannel.send(UserBehaviorEvents.VideoChosen("Excellent, good choice!"))
    }

    private suspend fun getVideoList() {
        withContext(Dispatchers.IO) { getVideoListUseCase.execute() }.let {
            if (it != null) _videoList.value = it
        }
    }

    fun onVideoListChanged(adapter: VideoAdapter, list: List<Video>) {
        adapter.videoList = list
        adapter.notifyDataSetChanged()
    }

    fun onVideoClicked(
        openInApp: Boolean,
        navController: NavController,
        clickedVideo: Video,
        context: Context
    ) {
        triggerUserVideoChosenBehavior()
        if (openInApp) openVideoInApp(navController, clickedVideo)
        else openVideoInBrowser(clickedVideo, context)
    }


    private fun openVideoInApp(navController: NavController, clickedVideo: Video) {
        selectVideoUseCase.execute(clickedVideo)
        navController.navigate(R.id.action_videoListFragment_to_watchVideoFragment)
    }

    private fun openVideoInBrowser(clickedVideo: Video, context: Context) {
        val url = Uri.parse(clickedVideo.videoSource)
        val intent = Intent(Intent.ACTION_VIEW, url)
        context.startActivity(intent)
    }
}