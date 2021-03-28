package com.company.dilnoza.mvvm_hilt.extentions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

/**
 * Created by Sherzodbek Muhammadiev on 12.11.2020.
 */

fun <T,K> MediatorLiveData<K>.addSourceDisposable(liveData:LiveData<T>, observer: Observer<T>){
    addSource(liveData){
        observer.onChanged(it)
        removeSource(liveData)
    }
}