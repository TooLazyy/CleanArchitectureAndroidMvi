package ru.wearemad.cleanarcexm.di.contactslist

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.contactslist.ContactsListInteractor
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository

@Module
class ContactsListModule {

    @Provides
    fun provideContactsListInteractor(listRep: ContactListRepository, favsRep: FavoritesRepository)
            = ContactsListInteractor(listRep, favsRep)
}