package com.example.videoapp.presentation.videoListScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Video
import com.example.videoapp.R
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class VideoListFragment : Fragment() {

    private val viewModel by viewModel<VideoListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video_list, container, false)

        val videoRecyclerView = view.findViewById<RecyclerView>(R.id.video_list)

        val videoList = emptyList<Video>()
        val videoClickListener =
            VideoClickListener { viewModel.onVideoClicked(findNavController(), it) }

        val adapter: VideoAdapter = get { parametersOf(videoList, videoClickListener) }

        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = LinearLayoutManager(get(), RecyclerView.VERTICAL, false)

        viewModel.videoList.observe(viewLifecycleOwner) {
            viewModel.onVideoListChanged(adapter, it)
        }

        return view
    }

}