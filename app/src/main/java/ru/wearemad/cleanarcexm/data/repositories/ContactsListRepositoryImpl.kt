package ru.wearemad.cleanarcexm.data.repositories

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.mappers.ContactMapper
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
import ru.wearemad.cleanarcexm.domain.commands.GetContactsListCommand
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import ru.wearemad.cleanarcexm.extensions.applyObservableCompute
import javax.inject.Inject

@Screen
class ContactsListRepositoryImpl
@Inject constructor(
        private val appDatabase: AppDatabase,
        private val contactsListCommand: GetContactsListCommand,
        private val contactMapper: ContactMapper
) : ContactListRepository {

    override fun getContactsListFromServer(): Observable<List<Contact>> {
        //api call here
        return contactsListCommand.execute()
                .startWith(getContactsListFromCache())
                .doOnNext { updateContactsListCache(it) }
    }

    override fun getContactsListFromCache(): Observable<List<Contact>> {
        return Observable.fromCallable {
            Thread.sleep(2000L)
            appDatabase.userContactsDao().getContactsList()
        }
                .applyObservableCompute()
                .flatMap {
                    Observable.fromIterable(it)
                            .map {
                                contactMapper.fromEntity(it)
                            }
                            .toList()
                            .toObservable()
                }
                .filter { it.isNotEmpty() }
    }

    override fun updateContactsListCache(contacts: List<Contact>) {
        Observable.fromIterable(contacts)
                .observeOn(Schedulers.io())
                .map { contactMapper.toEntity(it) }
                .toList()
                .filter { it.isNotEmpty() }
                .map {
                    appDatabase.userContactsDao()
                            .updateContactsList(it)
                    0
                }
                .ignoreElement()
                .subscribe(
                        {},
                        {
                            it.printStackTrace()
                        }
                )
    }
}