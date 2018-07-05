package ru.wearemad.cleanarcexm.data

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.api.ContactsApi
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails
import java.util.*
import javax.inject.Singleton

class ApiMock : ContactsApi {

    val contacts = mutableListOf<Contact>()
    private val random = Random()

    init {
        for(i in 0..15) {
            contacts.add(Contact(i.toLong(), "Name $i", "Surname $i",
                    "phone $i", "photo $i"))
        }
    }

    override fun getContacts(): Observable<List<Contact>> {
        return Observable.fromCallable { contacts }
    }

    override fun getContactDetails(contactId: Long): Observable<ContactDetails> {
        return Observable.fromCallable { createDetailsContact(getContactById(contactId)!!) }
    }

    private fun createDetailsContact(contact: Contact): ContactDetails {
        return ContactDetails(contact.id, contact.name, contact.surname,
                contact.phone, contact.photo, "job ${getRandomNumber()}",
                "address ${getRandomNumber()}")
    }

    private fun getContactById(contactId: Long) = contacts.firstOrNull { it.id == contactId }

    private fun getRandomNumber() = random.nextInt(50)
}