package com.example.videoapp.presentation.videoListScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.domain.models.Video
import com.example.domain.useCases.GetVideoListUseCase
import com.example.domain.useCases.SelectVideoUseCase
import com.example.videoapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoListViewModel(
    private val getVideoListUseCase: GetVideoListUseCase,
    private val selectVideoUseCase: SelectVideoUseCase
) : ViewModel() {

    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>> = _videoList

    init {
        viewModelScope.launch {
            getVideoList()
        }
    }

    private suspend fun getVideoList() {
        withContext(Dispatchers.IO){getVideoListUseCase.execute()}.let {
            if (it != null) _videoList.value = it
        }
    }

    fun onVideoListChanged(adapter: VideoAdapter, list: List<Video>) {
        adapter.videoList = list
        adapter.notifyDataSetChanged()
    }

    fun onVideoClicked(navController: NavController, clickedVideo: Video) {
        selectVideoUseCase.execute(clickedVideo)
        navController.navigate(R.id.action_videoListFragment_to_watchVideoFragment)
    }
}