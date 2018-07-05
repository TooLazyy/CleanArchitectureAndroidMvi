package ru.wearemad.cleanarcexm.di.contactdetails

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.contactsdetail.ContactsDetailsInteractor
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository

@Module
class ContactDetailsModule {

    @Provides
    fun provideContactDetailsInteractor(repository: ContactDetailsRepository)
            = ContactsDetailsInteractor(repository)
}