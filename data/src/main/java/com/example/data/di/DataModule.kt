package com.example.data.di

import androidx.room.Room
import com.example.data.api.HttpRoutes
import com.example.data.api.VideoApi
import com.example.data.database.AppDatabase
import com.example.data.database.AppDatabaseHelper
import com.example.data.repository.DataClassMapper
import com.example.data.repository.VideoRepositoryImpl
import com.example.domain.repository.VideoRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<VideoRepository> { VideoRepositoryImpl(get(), get(), get()) }


    factory<VideoApi> {
        Retrofit.Builder().baseUrl(HttpRoutes.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VideoApi::class.java)
    }

    factory { DataClassMapper() }

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "App.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single {
        AppDatabaseHelper(get())
    }

}
