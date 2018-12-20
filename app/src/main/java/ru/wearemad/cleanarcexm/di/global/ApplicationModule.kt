package ru.wearemad.cleanarcexm.di.global

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class  ApplicationModule(private val context: Application) {

    @Provides
    @Singleton
    fun provideAppContext(): Application = context
}