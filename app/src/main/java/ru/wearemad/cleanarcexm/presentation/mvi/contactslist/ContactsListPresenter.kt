package ru.wearemad.cleanarcexm.presentation.mvi.contactslist

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactslist.ContactsListInteractor
import javax.inject.Inject

@Screen
class ContactsListPresenter
@Inject constructor(
        private val interactor: ContactsListInteractor)
    : MviBasePresenter<ContactsListView, ContactsListVS>() {

    override fun bindIntents() {
        interactor.getContacts()
                .filter { it.isNotEmpty() }
                .subscribe(
                        {
                            if (it.isNotEmpty()) {

                            }
                        },
                        {
                            it.printStackTrace()
                        }
                )
    }
}