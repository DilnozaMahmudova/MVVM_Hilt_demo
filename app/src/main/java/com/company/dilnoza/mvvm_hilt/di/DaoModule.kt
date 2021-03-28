package com.company.dilnoza.mvvm_hilt.di

import com.company.dilnoza.mvvm_hilt.room.AppDatabase
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun getTaskDao(database: AppDatabase):TaskDao=database.taskDao()
}