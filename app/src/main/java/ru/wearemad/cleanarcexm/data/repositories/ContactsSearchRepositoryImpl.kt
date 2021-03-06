package ru.wearemad.cleanarcexm.data.repositories

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactsSearchRepository
import ru.wearemad.cleanarcexm.extensions.applyObservableCompute
import javax.inject.Inject
import javax.inject.Singleton

@Screen
class ContactsSearchRepositoryImpl
@Inject constructor() : ContactsSearchRepository {

    init {
        Log.d("MIINE", "init ContactsSearchRepositoryImpl")
    }

    override fun searchContacts(query: String, contacts: List<Contact>): Observable<List<Contact>> {
        return Observable.fromCallable { contacts }
                .subscribeOn(Schedulers.computation())
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
                .observeOn(AndroidSchedulers.mainThread())
    }
}