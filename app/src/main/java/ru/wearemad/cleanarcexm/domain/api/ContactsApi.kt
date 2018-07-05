package ru.wearemad.cleanarcexm.domain.api

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails

interface ContactsApi {

    fun getContacts(): Observable<List<Contact>>

    fun getContactDetails(contactId: Long): Observable<ContactDetails>
}