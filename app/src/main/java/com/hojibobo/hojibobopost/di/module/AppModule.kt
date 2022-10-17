package com.hojibobo.hojibobopost.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.hojibobo.hojibobopost.base.PreferencesManager
import com.hojibobo.hojibobopost.rx.AppSchedulerProvider
import com.sendbird.sbsample.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun bindContext(application: Application): Context {
        return application
    }

    @Provides
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }

    @Provides
    internal fun bindSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}