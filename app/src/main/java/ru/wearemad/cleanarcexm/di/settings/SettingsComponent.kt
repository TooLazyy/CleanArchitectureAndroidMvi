package ru.wearemad.cleanarcexm.di.settings

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.presentation.mvi.settings.SettingsPresenter

@Screen
@Component(modules = [(SettingsModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface SettingsComponent {

    fun presenter(): SettingsPresenter
}