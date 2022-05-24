package com.example.videoapp.presentation.watchVideoScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.videoapp.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel


class WatchVideoFragment : Fragment() {

    private val viewModel by viewModel<WatchVideoViewModel>()
    private val exoPlayer: ExoPlayer = get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watch_video, container, false)

        val player = view.findViewById<StyledPlayerView>(R.id.player)
        val backButton = view.findViewById<Button>(R.id.back_button)

        player.player = exoPlayer

        lifecycleScope.launchWhenCreated {
            viewModel.currentVideo.collectLatest {
                viewModel.startCurrentVideo(exoPlayer)
            }
        }

        backButton.setOnClickListener {
            viewModel.onBackButtonClicked(findNavController(), exoPlayer)
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.stop()
    }

}