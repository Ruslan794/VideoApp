package com.example.videoapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.videoapp.R
import com.example.videoapp.presentation.videoListScreen.VideoListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findNavController(R.id.app_nav_graph).popBackStack()
    }
}