package com.konkuk.summerhackathon

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HackathonApplication : Application() {
    override fun onCreate(){
        super.onCreate()
    }
}