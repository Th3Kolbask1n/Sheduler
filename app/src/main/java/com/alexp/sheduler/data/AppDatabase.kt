package com.alexp.sheduler.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AttendanceRecordDbModel::class], version = 1, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun schedulerDao() : SchedulerDao

    companion object {
        private var INSTANCE : AppDatabase? = null
        private var LOCK = Any()

        private const val DB_NAME = "attendance_record_db"

        fun getInstance(application: Application) : AppDatabase
        {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                val db = Room.databaseBuilder(
                    application,AppDatabase::class.java, DB_NAME).build()

                INSTANCE = db
                return db
            }


        }

    }

}