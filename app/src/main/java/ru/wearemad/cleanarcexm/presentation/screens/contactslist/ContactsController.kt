package ru.wearemad.cleanarcexm.presentation.screens.contactslist

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
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

    private val refreshContactsIntent = PublishSubject.create<Unit>()
    private val loading by bind<View>(R.id.loading)
    private val recycler by bind<RecyclerView>(R.id.recycler)
    private var adapter: ContactsListAdapter? = null

    override fun getLayoutId() = R.layout.screen_contacts_list

    override fun initPresenter() = DaggerContactsListComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactsListModule(ContactsListModule())
            .build()
            .presenter()

    override fun onViewCreated(itemView: View) {
        refreshContactsIntent.onNext(Unit)
        Log.d("MIINE", "created view")
    }

    override fun render(state: BaseViewState) {
        when(state) {
            is ContactsListVS.LoadingState -> {
                Log.d("MIINE", "loading")
                //show loading only if adapter is empty
                if ((adapter?.itemCount ?: 0) == 0) {
                    loading.visibility = View.VISIBLE
                }
            }
            is ContactsListVS.ErrorState -> {
                Log.d("MIINE", "error")
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
            is ContactsListVS.DataState -> {
                Log.d("MIINE", "data")
                loading.visibility = View.GONE
                if (adapter == null) {
                    adapter = ContactsListAdapter(activity as Context,
                            state.contacts.toMutableList())
                    recycler.layoutManager = LinearLayoutManager(activity)
                    recycler.adapter = adapter
                    adapter!!.onItemClick = {
                        openDetailedContact(it)
                    }
                } else {
                    adapter!!.updateData(state.contacts)
                }
            }
        }
    }

    override fun openDetailedContact(id: Long) {
        router.pushController(
                RouterTransaction.with(
                        ContactDetailsController(id)
                ).tag(ContactDetailsController.TAG)
                        .pushChangeHandler(FadeChangeHandler())
                        .popChangeHandler(FadeChangeHandler())
        )
    }

    override fun setRetainMode() {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }

    override fun loadContactsIntent() = refreshContactsIntent
}