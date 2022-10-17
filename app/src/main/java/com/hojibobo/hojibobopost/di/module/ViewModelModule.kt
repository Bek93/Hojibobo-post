package com.hojibobo.hojibobopost.di.module

import androidx.lifecycle.ViewModel
import com.hojibobo.hojibobopost.MainViewModel
import com.hojibobo.hojibobopost.di.annotation.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel




}