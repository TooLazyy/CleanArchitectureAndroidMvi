package ru.wearemad.cleanarcexm.di.main

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.presentation.mvi.main.MainPresenter

@Screen
@Component(modules = [(MainModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface MainComponent {

    fun presenter(): MainPresenter
}