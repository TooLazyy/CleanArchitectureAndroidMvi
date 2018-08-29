package ru.wearemad.cleanarcexm.domain.contactssearch

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository
import javax.inject.Inject

@Screen
class ContactsSearchInteractor
@Inject constructor(
        private val repository: ContactsSearchRepository,
        private val favsRep: FavoritesRepository
) {

    fun search(query: String, contacts: List<Contact>) =
            repository.searchContacts(query, contacts)

    fun updateContactFavorite(id: Long, isFavorite: Boolean) =
            favsRep.updateContactFavorite(id, isFavorite)

    fun getFavorites() = favsRep.getFavoritesList()
}