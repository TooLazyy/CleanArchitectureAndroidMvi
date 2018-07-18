package ru.wearemad.cleanarcexm.presentation.mvi.contactslist

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactslist.ContactsListInteractor
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import javax.inject.Inject

@Screen
class ContactsListPresenter
@Inject constructor(
        private val interactor: ContactsListInteractor)
    : MviBasePresenter<ContactsListView, ContactsListVS>() {

    override fun bindIntents() {
        val state: Observable<ContactsListVS> =
                intent(ContactsListView::loadContactsIntent)
                        .flatMap {
                            interactor.getContacts()
                                    .map {
                                        ContactsListVS.DataState(it)
                                    }
                                    .cast(ContactsListVS::class.java)
                                    .startWith(ContactsListVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsListVS.ErrorState(it.message ?: "")
                                    }
                        }
        subscribeViewState(state, ContactsListView::render)
    }
}