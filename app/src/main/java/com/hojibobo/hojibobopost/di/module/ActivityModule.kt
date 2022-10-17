package com.hojibobo.hojibobopost.di.module

import com.hojibobo.hojibobopost.MainActivity
import com.hojibobo.hojibobopost.SplashActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindSplashActivity(): SplashActivity



    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity


}