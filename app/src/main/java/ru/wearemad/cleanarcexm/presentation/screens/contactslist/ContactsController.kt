package ru.wearemad.cleanarcexm.presentation.screens.contactslist

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import io.reactivex.subjects.PublishSubject
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.contactslist.ContactsListModule
import ru.wearemad.cleanarcexm.di.contactslist.DaggerContactsListComponent
import ru.wearemad.cleanarcexm.extensions.bindView
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListVS
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.screens.contactsdetails.ContactDetailsController
import ru.wearemad.cleanarcexm.presentation.screens.contactssearch.ContactsSearchController

class ContactsController : BaseController<ContactsListView, ContactsListPresenter, ContactsListVS>(),
        ContactsListView {

    private val refreshContactsIntent = PublishSubject.create<Unit>()

    private val ivSearch: View by bindView(R.id.ivSearch)
    private val loading: View by bindView(R.id.loading)
    private val recycler: RecyclerView by bindView(R.id.recycler)

    private var adapter: ContactsListAdapter? = null

    override fun getLayoutId() = R.layout.screen_contacts_list

    override fun initPresenter() = DaggerContactsListComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactsListModule(ContactsListModule())
            .build()
            .presenter()

    override fun onViewCreated(itemView: View) {
        refreshContactsIntent.onNext(Unit)
        ivSearch.setOnClickListener {
            router.pushController(
                    RouterTransaction.with(
                            ContactsSearchController(adapter?.data ?: listOf())
                    ).tag(ContactsSearchController.TAG)
                            .pushChangeHandler(FadeChangeHandler())
                            .popChangeHandler(FadeChangeHandler())
            )
        }
    }

    override fun render(state: BaseViewState) {
        when (state) {
            is ContactsListVS.LoadingState -> {
                //show loading only if adapter is empty
                if ((adapter?.itemCount ?: 0) == 0) {
                    loading.visibility = View.VISIBLE
                }
            }
            is ContactsListVS.ErrorState -> {
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
            is ContactsListVS.DataState -> {
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