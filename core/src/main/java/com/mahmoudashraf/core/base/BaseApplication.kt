package com.mahmoudashraf.core.base

import android.app.Application
import android.content.Context

lateinit var appContext: Context

abstract class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}