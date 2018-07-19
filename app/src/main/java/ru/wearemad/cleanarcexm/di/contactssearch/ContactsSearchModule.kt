package ru.wearemad.cleanarcexm.di.contactssearch

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.contactssearch.ContactsSearchInteractor
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository

@Module
class ContactsSearchModule(private val contacts: List<Contact>) {

    @Provides
    fun provideContactsList(): List<Contact> = contacts

    @Provides
    fun provideContactsSearchInteractor(repository: ContactsSearchRepository)
            = ContactsSearchInteractor(repository)
}