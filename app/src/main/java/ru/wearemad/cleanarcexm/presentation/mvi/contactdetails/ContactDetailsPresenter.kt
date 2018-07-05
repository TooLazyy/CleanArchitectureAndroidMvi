package ru.wearemad.cleanarcexm.presentation.mvi.contactdetails

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.contactsdetail.ContactsDetailsInteractor
import javax.inject.Inject

@Screen
class ContactDetailsPresenter
    @Inject constructor(
            private val interactor: ContactsDetailsInteractor
    ): MviBasePresenter<ContactDetailsView, ContactsDetailsVS>() {

    override fun bindIntents() {

    }
}