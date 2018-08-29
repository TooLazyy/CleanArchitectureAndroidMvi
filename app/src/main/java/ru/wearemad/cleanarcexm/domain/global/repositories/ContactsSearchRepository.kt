package ru.wearemad.cleanarcexm.domain.global.repositories

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.Contact

interface ContactsSearchRepository {

    fun searchContacts(query: String, contacts: List<Contact>) : Observable<List<Contact>>
}