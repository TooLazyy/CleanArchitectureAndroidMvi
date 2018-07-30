package ru.wearemad.cleanarcexm.di.favorites

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesPresenter

@Screen
@Component(modules = [(FavoritesModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface FavoritesComponent {

    fun presenter(): FavoritesPresenter
}