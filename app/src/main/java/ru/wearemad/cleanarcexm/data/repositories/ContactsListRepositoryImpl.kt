package ru.wearemad.cleanarcexm.data.repositories

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.mappers.ContactMapper
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.commands.GetContactsListCommand
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactListRepository
import javax.inject.Inject

@Screen
class ContactsListRepositoryImpl
@Inject constructor(
        private val appDatabase: AppDatabase,
        private val contactsListCommand: GetContactsListCommand,
        private val contactMapper: ContactMapper
) : ContactListRepository {

    init {
        Log.d("MIINE", "init ContactsListRepositoryImpl")
    }

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
                .subscribeOn(Schedulers.computation())
                .map {
                    it.map {
                        contactMapper.fromEntity(it)
                    }
                }
                .filter { it.isNotEmpty() }
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun updateContactsListCache(contacts: List<Contact>) {
        Observable.fromCallable { contacts }
                .subscribeOn(Schedulers.computation())
                .map {
                    it.map {
                        contactMapper.toEntity(it)
                    }
                }
                .map {
                    appDatabase.userContactsDao()
                            .updateContactsList(it)
                }
                .ignoreElements()
                .subscribe({}, { it.printStackTrace() })
    }
}