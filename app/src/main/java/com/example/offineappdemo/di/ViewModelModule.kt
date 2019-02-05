package com.example.offineappdemo.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.offineappdemo.viewmodel.CommentViewModel
import com.example.offineappdemo.viewmodel.ViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CommentViewModel::class)
    abstract fun bindCouponViewModel(commentViewModel: CommentViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}