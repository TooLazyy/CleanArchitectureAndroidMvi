package ru.wearemad.cleanarcexm.domain.contactslist

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import javax.inject.Inject

@Screen
class ContactsListInteractor
@Inject constructor(
        private val contactsListRepository: ContactListRepository
) {

    fun getContacts() = contactsListRepository
            .getContactsListFromServer()
}