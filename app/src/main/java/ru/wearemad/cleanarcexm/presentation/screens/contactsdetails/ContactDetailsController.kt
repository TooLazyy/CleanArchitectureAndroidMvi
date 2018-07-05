package ru.wearemad.cleanarcexm.presentation.screens.contactsdetails

import android.view.View
import android.widget.Button
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.contactdetails.ContactDetailsModule
import ru.wearemad.cleanarcexm.di.contactdetails.DaggerContactDetailsComponent
import ru.wearemad.cleanarcexm.extensions.bind
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactDetailsPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactDetailsView
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactsDetailsVS
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

class ContactDetailsController :
        BaseController<ContactDetailsView, ContactDetailsPresenter, ContactsDetailsVS>(),
        ContactDetailsView {

    private val btn by bind<Button>(R.id.btn)

    override fun getLayoutId() = R.layout.screen_contact_details

    override fun initPresenter() = DaggerContactDetailsComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactDetailsModule(ContactDetailsModule())
            .build()
            .presenter()

    override fun onViewCreated(itemView: View) {

    }

    //come alive
    override fun onAttach(view: View) {
        super.onAttach(view)
        btn.setOnClickListener {
            router.popCurrentController()
        }
    }

    companion object {
        const val TAG = "ContactDetailsController"
    }

    override fun render(state: BaseViewState) {

    }
}