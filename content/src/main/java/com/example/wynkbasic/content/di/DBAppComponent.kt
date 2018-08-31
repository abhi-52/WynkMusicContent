package com.example.wynkbasic.content.di

import android.app.Application
import com.example.wynkbasic.content.ContentSDK
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ContentModule::class])
interface DBAppComponent{

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : DBAppComponent
    }

    fun inject(contentSDK: ContentSDK)
}