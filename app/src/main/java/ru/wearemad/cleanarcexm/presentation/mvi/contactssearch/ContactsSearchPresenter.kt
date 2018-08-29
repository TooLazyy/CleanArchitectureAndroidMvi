package ru.wearemad.cleanarcexm.presentation.mvi.contactssearch

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactssearch.ContactsSearchInteractor
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListVS
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Screen
class ContactsSearchPresenter
@Inject constructor(
        private val interactor: ContactsSearchInteractor,
        private val contacts: List<Contact>,
        private var favorites: HashSet<Long>
) : MviBasePresenter<ContactsSearchView, ContactsSearchVS>() {

    override fun bindIntents() {
        val state1: Observable<ContactsSearchVS> =
                intent(ContactsSearchView::updateContactIntent)
                        .flatMap {
                            interactor.updateContactFavorite(it.first, it.second)
                                    .flatMap {
                                        interactor.getFavorites()
                                    }
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        favorites = it
                                        ContactsSearchVS.UpdateContactState
                                    }
                                    .cast(ContactsSearchVS::class.java)
                                    .startWith(ContactsSearchVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsSearchVS.ErrorState(it.message ?: "")
                                    }
                        }
        val state2: Observable<ContactsSearchVS> =
                intent(ContactsSearchView::getSearchIntent)
                        .debounce(350, TimeUnit.MILLISECONDS)
                        .switchMap {
                            interactor.search(it, contacts)
                                    .map {
                                        ContactsSearchVS.DataState(Pair(it, favorites))
                                    }
                                    .cast(ContactsSearchVS::class.java)
                                    .startWith(ContactsSearchVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsSearchVS.ErrorState(it.message ?: "")
                                    }
                                    .observeOn(AndroidSchedulers.mainThread())
                        }

        val state = Observable.merge(
                state1,
                state2)
        subscribeViewState(state, ContactsSearchView::render)
    }
}