package ru.wearemad.cleanarcexm.di.global

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
import ru.wearemad.cleanarcexm.domain.commands.GetContactsListCommand

@Module
class DomainModule {

    @Provides
    fun provideContactsListCmd(api: ContactsApi):
            GetContactsListCommand = GetContactsListCommand(api)

    @Provides
    fun provideContactDetailssCmd(api: ContactsApi):
            GetContactDetailsCommand = GetContactDetailsCommand(api)
}