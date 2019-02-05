package com.example.offineappdemo.di

import com.example.offineappdemo.view.CommentsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeCommentsActivity(): CommentsActivity
}