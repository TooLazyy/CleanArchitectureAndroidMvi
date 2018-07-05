package ru.wearemad.cleanarcexm.di.contactslist

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListPresenter

@Screen
@Component(modules = [(ContactsListModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface ContactsListComponent {

    fun presenter(): ContactsListPresenter
}