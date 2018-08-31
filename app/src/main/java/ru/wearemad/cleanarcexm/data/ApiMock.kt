package ru.wearemad.cleanarcexm.data

import android.util.Log
import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails

class ApiMock : ContactsApi {

    private val contacts = mutableListOf<Contact>()

    init {
        Log.d("MIINE", "init api")
        for (i in 0..15) {
            contacts.add(Contact(i.toLong(), "Name $i", "Surname $i",
                    "phone $i", "photo $i"))
        }
    }

    override fun getContacts(): Observable<List<Contact>> {
        return Observable.fromCallable {
            Thread.sleep(5000L)
            contacts
        }
    }

    override fun getContactDetails(contactId: Long): Observable<ContactDetails> {
        return Observable.fromCallable {
            val contact = getContactById(contactId)
            createDetailsContact(contact!!)
        }
    }

    private fun createDetailsContact(contact: Contact) =
            ContactDetails(contact.id, contact.name, contact.surname,
                    contact.phone, contact.photo, "job ${contact.id}",
                    "address ${contact.id}")

    private fun getContactById(contactId: Long) = contacts.firstOrNull { it.id == contactId }
}