package com.websarva.wings.android.myapplication

import android.content.Context
import androidx.room.Room

// シングルトンパターンを使用してデータベースインスタンスを管理する
object DatabaseBuilder {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also {
                INSTANCE = it
            }
        }
    }

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "myapplication_database" // データベース名はアプリケーションに合わせて命名
        ).build()
    }
}