package ru.wearemad.cleanarcexm.di.global

import dagger.Component
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
import ru.wearemad.cleanarcexm.domain.commands.GetContactsListCommand
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class), (DataModule::class),
    (DomainModule::class)])
interface ApplicationComponent {

    fun contactsListRepository(): ContactListRepository

    fun contactDetailsRepository(): ContactDetailsRepository

    fun contactsListCmd(): GetContactsListCommand

    fun contactDetailsCmd(): GetContactDetailsCommand
}