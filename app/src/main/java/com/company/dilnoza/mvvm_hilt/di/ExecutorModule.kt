package com.company.dilnoza.mvvm_hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExecutorModule {
    @Provides
    @Singleton
    fun getExecutor(): ExecutorService = Executors.newSingleThreadExecutor()
}