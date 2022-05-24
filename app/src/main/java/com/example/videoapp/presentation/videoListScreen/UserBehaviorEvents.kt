package com.example.videoapp.presentation.videoListScreen

sealed class UserBehaviorEvents {
    data class Inaction(val message: String) : UserBehaviorEvents()
    data class VideoChosen(val message: String) : UserBehaviorEvents()
}