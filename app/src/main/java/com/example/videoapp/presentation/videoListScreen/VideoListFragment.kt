package com.example.videoapp.presentation.videoListScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.videoapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
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
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox)

        val videoClickListener =
            VideoClickListener {
                viewModel.onVideoClicked(
                    openInApp = !checkbox.isChecked,
                    navController = findNavController(),
                    clickedVideo = it,
                    context = requireContext()
                )
            }

        val adapter: VideoAdapter =
            get { parametersOf(viewModel.videoList.value, videoClickListener) }

        videoRecyclerView.adapter = adapter
        videoRecyclerView.layoutManager = LinearLayoutManager(get(), RecyclerView.VERTICAL, false)

        lifecycleScope.launchWhenStarted {
            viewModel.videoList.collectLatest {
                viewModel.onVideoListChanged(adapter, it)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.userBehaviorFlow.collect { behavior ->
                val message = when (behavior) {
                    is UserBehaviorEvents.Inaction -> behavior.message
                    is UserBehaviorEvents.VideoChosen -> behavior.message
                }
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenResumed {
            delay(10000)
            viewModel.triggerUserInactionBehavior()
        }

        return view
    }

}