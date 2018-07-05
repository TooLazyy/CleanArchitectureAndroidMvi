package ru.wearemad.cleanarcexm.domain.commands

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.domain.global.command.BaseCommand
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetContactDetailsCommand
    @Inject constructor(
            private val api: ContactsApi
    ): BaseCommand<ContactDetails>() {

    var contactdId: Long = -1L
    override fun serviceRequest(): Observable<ContactDetails> = api.getContactDetails(contactdId)
}