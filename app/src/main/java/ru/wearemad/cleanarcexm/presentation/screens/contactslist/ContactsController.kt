package ru.wearemad.cleanarcexm.presentation.screens.contactslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.contactslist.ContactsListModule
import ru.wearemad.cleanarcexm.di.contactslist.DaggerContactsListComponent
import ru.wearemad.cleanarcexm.extensions.bind
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListVS
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.screens.contactsdetails.ContactDetailsController

class ContactsController : BaseController<ContactsListView, ContactsListPresenter, ContactsListVS>(),
        ContactsListView {

    private val btn by bind<Button>(R.id.btn1)

    override fun getLayoutId() = R.layout.screen_contacts_list

    override fun initPresenter() = DaggerContactsListComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactsListModule(ContactsListModule())
            .build()
            .presenter()

    override fun onViewCreated(itemView: View) {
        btn.setOnClickListener {
            router.pushController(RouterTransaction.with(ContactDetailsController())
                    .tag(ContactDetailsController.TAG)
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    override fun render(state: BaseViewState) {

    }

    override fun setRetainMode() {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }
}