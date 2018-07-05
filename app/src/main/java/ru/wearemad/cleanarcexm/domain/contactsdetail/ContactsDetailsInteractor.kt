package ru.wearemad.cleanarcexm.domain.contactsdetail

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository
import javax.inject.Inject

@Screen
class ContactsDetailsInteractor
@Inject constructor(
        private val contactDetailsRepository: ContactDetailsRepository
) {

    fun getContactsDetails(contactId: Long) = contactDetailsRepository
            .getContactDetailsFromServer(contactId)
}