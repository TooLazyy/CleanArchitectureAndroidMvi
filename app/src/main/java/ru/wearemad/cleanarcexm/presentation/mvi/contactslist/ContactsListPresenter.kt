package ru.wearemad.cleanarcexm.presentation.mvi.contactslist

import android.util.Log
import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
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
        val state1: Observable<ContactsListVS> =
                intent(ContactsListView::loadContactsIntent)
                        .flatMap {
                            interactor.getContacts()
                                    .flatMap({_ -> interactor.getFavorites()},
                                            {contacts, favorites ->
                                                        Pair(contacts, favorites)
                                    })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        ContactsListVS.DataState(it)
                                    }
                                    .cast(ContactsListVS::class.java)
                                    .startWith(ContactsListVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsListVS.ErrorState(it.message ?: "")
                                    }
                        }

        val state2: Observable<ContactsListVS> =
                intent(ContactsListView::updateContactIntent)
                        .flatMap {
                            interactor.updateContactFavorite(it.first, it.second)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        ContactsListVS.UpdateContactState
                                    }
                                    .cast(ContactsListVS::class.java)
                                    .startWith(ContactsListVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsListVS.ErrorState(it.message ?: "")
                                    }
                        }

        val state3: Observable<ContactsListVS> =
                intent(ContactsListView::updateFavoritesIntent)
                        .flatMap {
                            interactor.getFavorites()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        ContactsListVS.UpdateFavoritesState(it)
                                    }
                                    .cast(ContactsListVS::class.java)
                                    .onErrorReturn {
                                        ContactsListVS.ErrorState(it.message ?: "")
                                    }
                        }
        val state = Observable.merge(
                state1,
                state2,
                state3)

        subscribeViewState(state, ContactsListView::render)
    }
}