package com.alexp.sheduler.di

import android.app.Application
import com.alexp.sheduler.data.AppDatabase
import com.alexp.sheduler.data.SchedulerDao
import com.alexp.sheduler.data.SchedulerRepositoryImpl
import com.alexp.sheduler.domain.SchedulerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides


@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindAttendanceRecordsRepository(impl:SchedulerRepositoryImpl) : SchedulerRepository

    companion object
    {
        @ApplicationScope
        @Provides
        fun provideShedulerDao(application: Application):SchedulerDao
        {
            return AppDatabase.getInstance(application).schedulerDao()
        }
    }
}