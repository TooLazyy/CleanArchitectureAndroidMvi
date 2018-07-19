package ru.wearemad.cleanarcexm.di.contactssearch

import dagger.Component
import ru.wearemad.cleanarcexm.di.global.ApplicationComponent
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.presentation.mvi.contactssearch.ContactsSearchPresenter

@Screen
@Component(modules = [(ContactsSearchModule::class)],
        dependencies = [(ApplicationComponent::class)])
interface ContactsSearchComponent {

    fun contacts(): List<Contact>

    fun presenter(): ContactsSearchPresenter
}