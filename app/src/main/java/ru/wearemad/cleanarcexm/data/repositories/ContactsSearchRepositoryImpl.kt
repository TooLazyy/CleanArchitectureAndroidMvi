package ru.wearemad.cleanarcexm.data.repositories

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import ru.wearemad.cleanarcexm.extensions.applyObservableCompute
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsSearchRepositoryImpl
@Inject constructor() : ContactsSearchRepository {

    override fun searchContacts(query: String, contacts: List<Contact>): Observable<List<Contact>> {
        return Observable.fromCallable { contacts }
                .applyObservableCompute()
                .map {
                    if (it.isEmpty()) {
                        contacts
                    } else {
                        it.filter {
                            it.name.contains(query, true) or
                                    it.surname.contains(query, true)
                        }
                    }
                }
    }
}