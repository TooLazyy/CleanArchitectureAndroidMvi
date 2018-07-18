package ru.wearemad.cleanarcexm.domain.global.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.Contact

interface ContactListRepository {

    fun getContactsListFromServer(): Observable<List<Contact>>

    fun getContactsListFromCache(): Observable<List<Contact>>

    fun updateContactsListCache(contacts: List<Contact>)
}