package com.example.offineappdemo

import android.app.Activity
import android.app.Application
import android.app.Service
import com.example.offineappdemo.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    override fun activityInjector() = dispatchingActivityInjector

    override fun serviceInjector() = dispatchingServiceInjector

    private fun initDagger() = AppInjector.init(this)
}