package ru.wearemad.cleanarcexm.di.contactdetails

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactDetailsPresenter

@Screen
@Component(modules = [(ContactDetailsModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface ContactDetailsComponent {

    fun presenter(): ContactDetailsPresenter
}