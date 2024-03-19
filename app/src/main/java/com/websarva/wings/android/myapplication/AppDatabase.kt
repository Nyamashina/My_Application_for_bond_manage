package com.websarva.wings.android.myapplication

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

@Database(entities = [Bond::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bondDao(): BondDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        /*
        //バージョンの統合用
        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bond_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }


        開発中にデータベーススキーマを頻繁に変更する場合、fallbackToDestructiveMigration()を使用すると便利です。これにより、スキーマの変更のたびに手動でデータベースを削除したり、マイグレーションを書いたりする手間を省くことができます。ただし、この方法は開発段階やテスト時にのみ推奨されます。実際のユーザーデータを扱うアプリケーションでこの方法を使用すると、スキーマが変更されるたびにユーザーのデータが失われるため、リリース版のアプリケーションでは避けるべきです。
        */

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bond_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }

        /*

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bond_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }

         */

    }
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE bond_database ADD COLUMN newcolumnname datatype")
    }
}