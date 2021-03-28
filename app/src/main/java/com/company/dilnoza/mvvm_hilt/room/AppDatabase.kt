package com.company.dilnoza.mvvm_hilt.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.company.dilnoza.mvvm_hilt.room.converter.Converter
import com.company.dilnoza.mvvm_hilt.room.dao.TaskDao
import com.company.dilnoza.mvvm_hilt.room.entity.TaskData

@Database(entities = [TaskData::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        lateinit var INSTANCE: AppDatabase

        fun init(context: Context): AppDatabase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}