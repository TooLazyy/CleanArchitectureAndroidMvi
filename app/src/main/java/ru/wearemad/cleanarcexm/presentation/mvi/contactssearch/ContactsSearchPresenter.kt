package ru.wearemad.cleanarcexm.presentation.mvi.contactssearch

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactssearch.ContactsSearchInteractor
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Screen
class ContactsSearchPresenter
@Inject constructor(
        private val interactor: ContactsSearchInteractor,
        private val contacts: List<Contact>
) : MviBasePresenter<ContactsSearchView, ContactsSearchVS>() {

    override fun bindIntents() {
        val state: Observable<ContactsSearchVS> =
                intent(ContactsSearchView::getSearchIntent)
                        .debounce(350, TimeUnit.MILLISECONDS)
                        .switchMap {
                            interactor.search(it, contacts)
                                    .map {
                                        ContactsSearchVS.DataState(it)
                                    }
                                    .cast(ContactsSearchVS::class.java)
                                    .startWith(ContactsSearchVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsSearchVS.ErrorState(it.message ?: "")
                                    }
                                    .observeOn(AndroidSchedulers.mainThread())
                        }

        subscribeViewState(state, ContactsSearchView::render)
    }
}