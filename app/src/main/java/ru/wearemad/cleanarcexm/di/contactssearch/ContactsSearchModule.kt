package ru.wearemad.cleanarcexm.di.contactssearch

import dagger.Module
import dagger.Provides
import ru.wearemad.cleanarcexm.domain.contactssearch.ContactsSearchInteractor
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository

@Module
class ContactsSearchModule(private val contacts: List<Contact>,
                           private val favorites: HashSet<Long>) {

    @Provides
    fun provideContactsList(): List<Contact> = contacts

    @Provides
    fun provideFavorites(): HashSet<Long> = favorites

    @Provides
    fun provideContactsSearchInteractor(rep1: ContactsSearchRepository, rep2: FavoritesRepository)
            = ContactsSearchInteractor(rep1, rep2)
}