package com.example.videoapp.di

import com.example.domain.models.Video
import com.example.videoapp.presentation.videoListScreen.VideoAdapter
import com.example.videoapp.presentation.videoListScreen.VideoClickListener
import com.example.videoapp.presentation.videoListScreen.VideoListFragment
import com.example.videoapp.presentation.videoListScreen.VideoListViewModel
import com.example.videoapp.presentation.watchVideoScreen.WatchVideoFragment
import com.example.videoapp.presentation.watchVideoScreen.WatchVideoViewModel
import com.google.android.exoplayer2.ExoPlayer
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    fragment { VideoListFragment() }
    fragment { WatchVideoFragment() }


    viewModel { VideoListViewModel(get(), get()) }
    viewModel { WatchVideoViewModel(get(), get()) }

    single { (list: List<Video>, videoClickListener: VideoClickListener) ->
        VideoAdapter(
            context = get(),
            videoList = list,
            clickListener = videoClickListener
        )
    }

    factory { ExoPlayer.Builder(get()).build() }
}