package ru.wearemad.cleanarcexm.presentation.screens.contactsdetails

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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

class ContactDetailsController:
        BaseController<ContactDetailsView, ContactDetailsPresenter, ContactsDetailsVS>,
        ContactDetailsView {

    private var contactId: Long = 0
    private var dataLoaded = false

    private val loading by bind<View>(R.id.loading)
    private val tvName by bind<TextView>(R.id.tvName)
    private val tvPhone by bind<TextView>(R.id.tvPhone)
    private val tvJob by bind<TextView>(R.id.tvJob)
    private val tvAddress by bind<TextView>(R.id.tvAddress)

    private val loadDetailsIntent = PublishSubject.create<Long>()

    constructor(id: Long): super() {
        contactId = id
    }

    constructor() : super()

    override fun getLayoutId() = R.layout.screen_contact_details

    override fun initPresenter() = DaggerContactDetailsComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactDetailsModule(ContactDetailsModule())
            .build()
            .presenter()

    override fun onViewCreated(itemView: View) {
        loadDetailsIntent.onNext(contactId)
    }

    companion object {
        const val TAG = "ContactDetailsController"
    }

    override fun render(state: BaseViewState) {
        when(state) {
            is ContactsDetailsVS.LoadingState -> {
                Log.d("MIINE", "loading d")
                if (dataLoaded.not()) {
                    loading.visibility = View.VISIBLE
                }
            }
            is ContactsDetailsVS.DataState -> {
                Log.d("MIINE", "data d")
                loading.visibility = View.GONE
                dataLoaded =  true

                tvName.text = "Name: ${state.contact.name} ${state.contact.surname}"
                tvPhone.text = "Phone: ${state.contact.phone}"
                tvJob.text = "Job: ${state.contact.job}"
                tvAddress.text = "Job: ${state.contact.address}"
            }
            is ContactsDetailsVS.ErrorState -> {
                Log.d("MIINE", "error d")
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun loadDetailIntent() = loadDetailsIntent
}