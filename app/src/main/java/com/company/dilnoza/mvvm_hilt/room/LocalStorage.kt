package com.company.dilnoza.mvvm_hilt.room

import android.content.Context


class LocalStorage private constructor(context: Context) {

    companion object {
        lateinit var instance: LocalStorage
        fun init(context: Context) {
            instance =
                LocalStorage(context)

        }
    }

    private var pref = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var name by StringPreference(pref)
}