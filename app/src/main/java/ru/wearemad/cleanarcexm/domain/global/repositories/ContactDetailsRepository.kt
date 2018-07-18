package ru.wearemad.cleanarcexm.domain.global.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails

interface ContactDetailsRepository {

    fun getContactDetailsFromServer(contactId: Long): Observable<ContactDetails>

    fun getContactDetailsFromCache(contactId: Long): Observable<ContactDetails>

    fun updateContactDetailsCache(contact: ContactDetails)
}