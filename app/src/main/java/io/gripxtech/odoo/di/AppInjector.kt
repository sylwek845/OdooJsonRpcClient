package io.gripxtech.odoo.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.gripxtech.odoo.App
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class
    ]
)

interface AppInjector {

    fun inject(app: App)

    @Component.Builder
    interface Builder {

        fun build(): AppInjector

        @BindsInstance
        fun application(app: App): Builder


    }
}