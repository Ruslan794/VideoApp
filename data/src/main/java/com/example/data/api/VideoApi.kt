package com.example.data.api


import com.example.data.api.models.VideoApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {
    @GET(HttpRoutes.VIDEOS)
    suspend fun getQuotes(): Response<VideoApiResponse>
}
