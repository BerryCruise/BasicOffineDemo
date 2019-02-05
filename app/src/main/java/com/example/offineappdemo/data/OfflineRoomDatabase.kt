package com.example.offineappdemo.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.offineappdemo.domain.Constants
import com.example.offineappdemo.model.Comment

@Database(entities = [Comment::class], version = 1)
abstract class OfflineRoomDatabase : RoomDatabase() {

    abstract fun commentDao(): CommentDao

    companion object {

        @Volatile
        private var INSTANCE: OfflineRoomDatabase? = null

        fun getInstance(context: Context): OfflineRoomDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room
                                .databaseBuilder(context.applicationContext, OfflineRoomDatabase::class.java, Constants.DB_NAME)
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}