package com.hojibobo.hojibobopost.di.component

import android.app.Application
import com.hojibobo.hojibobopost.AppApplication
import com.hojibobo.hojibobopost.di.module.ActivityModule
import com.hojibobo.hojibobopost.di.module.AppModule
import com.hojibobo.hojibobopost.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: AppApplication?)
}