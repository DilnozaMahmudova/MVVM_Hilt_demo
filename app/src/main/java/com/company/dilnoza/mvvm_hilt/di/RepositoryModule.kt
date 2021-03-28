package com.company.dilnoza.mvvm_hilt.di

import com.company.dilnoza.mvvm_hilt.contract.*
import com.company.dilnoza.mvvm_hilt.model.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface  RepositoryModule {
    @Binds
    @Singleton
    fun getAddRepository(repository:AddRepository):AddContract.Model
    @Binds
    @Singleton
    fun getAllTaskRepository(repository:AllTaskRepository):AllTaskContract.Model
    @Binds
    @Singleton
    fun getBasketRepository(repository:BasketRepository):BasketContract.Model
    @Binds
    @Singleton
    fun getEditRepository(repository: EditRepository):EditContract.Model
    @Binds
    @Singleton
    fun getHistoryRepository(repository: HistoryRepository):HistoryContract.Model
    @Binds
    @Singleton
    fun getMainRepository(repository: MainRepository):MainContracts.Model
}

