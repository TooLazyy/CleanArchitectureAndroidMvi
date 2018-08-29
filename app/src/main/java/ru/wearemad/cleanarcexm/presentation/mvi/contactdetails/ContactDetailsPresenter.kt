package ru.wearemad.cleanarcexm.presentation.mvi.contactdetails

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactsdetail.ContactsDetailsInteractor
import javax.inject.Inject

@Screen
class ContactDetailsPresenter
    @Inject constructor(
            private val interactor: ContactsDetailsInteractor
    ): MviBasePresenter<ContactDetailsView, ContactsDetailsVS>() {

    override fun bindIntents() {
        val state: Observable<ContactsDetailsVS> =
                intent(ContactDetailsView::loadDetailIntent)
                        .flatMap {
                            interactor.getContactsDetails(it)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        ContactsDetailsVS.DataState(it)
                                    }
                                    .cast(ContactsDetailsVS::class.java)
                                    .startWith(ContactsDetailsVS.LoadingState)
                                    .onErrorReturn {
                                        ContactsDetailsVS.ErrorState(it.message ?: "")
                                    }
                        }
        subscribeViewState(state, ContactDetailsView::render)
    }
}