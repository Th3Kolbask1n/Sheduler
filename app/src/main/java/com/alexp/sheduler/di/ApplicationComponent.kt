package com.alexp.sheduler.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexp.sheduler.presentation.AttendanceRecordActivity
import com.alexp.sheduler.presentation.AttendanceRecordFragment
import com.alexp.sheduler.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(
    modules = [ViewModelModule::class]
)


interface ApplicationComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: AttendanceRecordFragment)
    fun inject(activity: AttendanceRecordActivity)

    @Component.Factory
    interface Factory{
        fun create(
        @BindsInstance application: Application
    ) : ApplicationComponent
}
}