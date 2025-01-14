package com.alexp.sheduler.presentation

import android.app.Application
import com.alexp.sheduler.di.DaggerApplicationComponent



class AttendanceApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}