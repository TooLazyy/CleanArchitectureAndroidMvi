package ru.wearemad.cleanarcexm.domain.contactssearch

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import javax.inject.Inject

@Screen
class ContactsSearchInteractor
@Inject constructor(
        private val repository: ContactsSearchRepository
) {

    fun search(query: String, contacts: List<Contact>) =
            repository.searchContacts(query, contacts)
}