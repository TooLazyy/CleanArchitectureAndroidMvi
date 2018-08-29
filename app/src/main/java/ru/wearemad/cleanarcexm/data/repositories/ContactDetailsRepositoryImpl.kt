package ru.wearemad.cleanarcexm.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.data.database.db.AppDatabase
import ru.wearemad.cleanarcexm.data.database.mappers.ContactDetailsMapper
import ru.wearemad.cleanarcexm.domain.commands.GetContactDetailsCommand
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails
import ru.wearemad.cleanarcexm.domain.global.repositories.ContactDetailsRepository
import ru.wearemad.cleanarcexm.extensions.applyObservableCompute
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactDetailsRepositoryImpl
@Inject constructor(
        private val appDatabase: AppDatabase,
        private val getContactDetailsCommand: GetContactDetailsCommand,
        private val contactMapper: ContactDetailsMapper
) : ContactDetailsRepository {

    override fun getContactDetailsFromServer(contactId: Long): Observable<ContactDetails> {
        //api call here
        getContactDetailsCommand.contactdId = contactId
        return getContactDetailsCommand.execute()
                .startWith(getContactDetailsFromCache(contactId))
                .doOnNext {
                    updateContactDetailsCache(it)
                }
    }

    override fun getContactDetailsFromCache(contactId: Long): Observable<ContactDetails> {
        return Observable.fromCallable { appDatabase.userContactsDao().getContactDetails(contactId) }
                .subscribeOn(Schedulers.computation())
                .filter { it.isNotEmpty() }
                .map {
                    contactMapper.fromEntity(it[0])
                }
    }

    override fun updateContactDetailsCache(contact: ContactDetails) {
        Observable.fromCallable { contactMapper.toEntity(contact) }
                .subscribeOn(Schedulers.io())
                .map {
                    appDatabase.userContactsDao().updateContactDetails(it)
                    0
                }
                .ignoreElements()
                .subscribe(
                        {},
                        {
                            it.printStackTrace()
                        }
                )
    }
}