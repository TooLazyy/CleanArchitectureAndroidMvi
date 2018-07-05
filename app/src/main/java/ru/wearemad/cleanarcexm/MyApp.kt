package ru.wearemad.cleanarcexm

import android.app.Application
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.ApplicationModule
import ru.wearemad.cleanarcexm.di.global.DaggerApplicationComponent

class MyApp : Application() {

    companion object {

        lateinit var instance: MyApp
            private set
        lateinit var appComponent: ApplicationComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}