package ru.wearemad.cleanarcexm.presentation.screens.contactsdetails

import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.reactivex.subjects.PublishSubject
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.contactdetails.ContactDetailsModule
import ru.wearemad.cleanarcexm.di.contactdetails.DaggerContactDetailsComponent
import ru.wearemad.cleanarcexm.extensions.bindView
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactDetailsPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactDetailsView
import ru.wearemad.cleanarcexm.presentation.mvi.contactdetails.ContactsDetailsVS
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

class ContactDetailsController :
        BaseController<ContactDetailsView, ContactDetailsPresenter, ContactsDetailsVS>,
        ContactDetailsView {

    private var contactId: Long = 0
    private var dataLoaded = false

    private val loading: View by bindView(R.id.loading)
    private val tvName: TextView by bindView(R.id.tvName)
    private val tvPhone: TextView by bindView(R.id.tvPhone)
    private val tvJob: TextView by bindView(R.id.tvJob)
    private val tvAddress: TextView by bindView(R.id.tvAddress)

    private val loadDetailsIntent = PublishSubject.create<Long>()

    constructor(id: Long) : super() {
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
        when (state) {
            is ContactsDetailsVS.LoadingState -> {
                if (dataLoaded.not()) {
                    loading.visibility = View.VISIBLE
                }
            }
            is ContactsDetailsVS.DataState -> {
                loading.visibility = View.GONE
                dataLoaded = true

                tvName.text = "Name: ${state.contact.name} ${state.contact.surname}"
                tvPhone.text = "Phone: ${state.contact.phone}"
                tvJob.text = "Job: ${state.contact.job}"
                tvAddress.text = "Job: ${state.contact.address}"
            }
            is ContactsDetailsVS.ErrorState -> {
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun loadDetailIntent() = loadDetailsIntent
}