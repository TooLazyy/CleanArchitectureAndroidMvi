package ru.wearemad.cleanarcexm.di.global

import dagger.Component
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (DataModule::class),
    (DomainModule::class)])
interface ApplicationComponent {

    fun contactsListRepository(): ContactListRepository

    fun contactDetailsRepository(): ContactDetailsRepository

    fun contactsSearchRepository(): ContactsSearchRepository
}