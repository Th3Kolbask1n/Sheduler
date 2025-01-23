package com.alexp.sheduler.di

import androidx.lifecycle.ViewModel
import com.alexp.sheduler.presentation.AttendanceRecordViewModel
import com.alexp.sheduler.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface ViewModelModule   {

    @Binds
    @IntoMap
    @ViewModelKey(AttendanceRecordViewModel::class)
    fun bindAttendanceRecordViewModel(viewModel: AttendanceRecordViewModel):ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel):ViewModel
}