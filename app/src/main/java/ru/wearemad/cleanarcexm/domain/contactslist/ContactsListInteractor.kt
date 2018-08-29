package ru.wearemad.cleanarcexm.domain.contactslist

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository
import javax.inject.Inject

@Screen
class ContactsListInteractor
@Inject constructor(
        private val listRep: ContactListRepository,
        private val favsRep: FavoritesRepository
) {

    fun getContacts() = listRep
            .getContactsListFromServer()

    fun updateContactFavorite(id: Long, isFavorite: Boolean) =
            favsRep.updateContactFavorite(id, isFavorite)

    fun getFavorites() = favsRep.getFavoritesList()
}