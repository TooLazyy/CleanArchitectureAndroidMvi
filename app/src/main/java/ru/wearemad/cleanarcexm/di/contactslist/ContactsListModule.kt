package ru.wearemad.cleanarcexm.di.contactslist

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.contactslist.ContactsListInteractor
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository

@Module
class ContactsListModule {

    @Provides
    fun provideContactsListInteractor(repository: ContactListRepository)
            = ContactsListInteractor(repository)
}