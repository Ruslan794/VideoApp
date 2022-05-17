package com.example.videoapp.di

import com.example.domain.useCases.GetVideoListUseCase
import com.example.domain.useCases.SelectVideoUseCase
import com.example.domain.useCases.ShowSelectedVideoUseCase
import org.koin.dsl.module

val domainModule = module {

    factory { GetVideoListUseCase(get()) }
    factory { SelectVideoUseCase(get()) }
    factory { ShowSelectedVideoUseCase(get()) }
}
