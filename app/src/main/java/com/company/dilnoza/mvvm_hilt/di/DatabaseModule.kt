package com.company.dilnoza.mvvm_hilt.di

import com.company.dilnoza.mvvm_hilt.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun getDataBase(): AppDatabase =AppDatabase.INSTANCE

}