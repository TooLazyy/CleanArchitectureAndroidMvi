package ru.wearemad.cleanarcexm.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.mappers.ContactMapper
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
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

    override fun getContactsListFromServer(): Observable<List<Contact>> {
        //api call here
        return contactsListCommand.execute()
                .startWith(getContactsListFromCache())
                .doOnNext {
                    updateContactsListCache(it)
                }
    }

    override fun getContactsListFromCache(): Observable<List<Contact>> {
        return Observable.fromCallable { appDatabase.userContactsDao().getContactsList() }
                .subscribeOn(Schedulers.computation())
                .flatMap { Observable.fromIterable(it)
                        .map { contactMapper.fromEntity(it) }
                        .toList()
                        .toObservable()
                }
    }

    override fun updateContactsListCache(contacts: List<Contact>): Completable {
        return Observable.fromIterable(contacts)
                .subscribeOn(Schedulers.io())
                .map { contactMapper.toEntity(it) }
                .toList()
                .map {
                    appDatabase.userContactsDao()
                            .updateContactsList(it)
                    0
                }
                .ignoreElement()
    }
}