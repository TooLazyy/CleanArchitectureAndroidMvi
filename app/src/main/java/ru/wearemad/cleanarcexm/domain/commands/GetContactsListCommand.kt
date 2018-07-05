package ru.wearemad.cleanarcexm.domain.commands

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.domain.global.command.BaseCommand
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetContactsListCommand
    @Inject constructor(
            private val api: ContactsApi
    ): BaseCommand<List<Contact>>() {

    override fun serviceRequest(): Observable<List<Contact>> = api.getContacts()
}