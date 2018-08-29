package ru.wearemad.cleanarcexm.presentation.screens.contactssearch

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import io.reactivex.subjects.PublishSubject
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.contactssearch.ContactsSearchModule
import ru.wearemad.cleanarcexm.di.contactssearch.DaggerContactsSearchComponent
import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.extensions.bindView
import ru.wearemad.cleanarcexm.presentation.mvi.contactssearch.ContactsSearchPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.contactssearch.ContactsSearchVS
import ru.wearemad.cleanarcexm.presentation.mvi.contactssearch.ContactsSearchView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.screens.contactsdetails.ContactDetailsController
import ru.wearemad.cleanarcexm.presentation.screens.contactslist.ContactsListAdapter

class ContactsSearchController :
        BaseController<ContactsSearchView, ContactsSearchPresenter, ContactsSearchVS>,
        ContactsSearchView {

    companion object {
        const val TAG = "ContactsSearchController"
    }

    private var mContacts = listOf<Contact>()
    private var mFavorites = hashSetOf<Long>()

    constructor() : super()

    constructor(contacts: List<Contact> = listOf(),
                favs: HashSet<Long> = hashSetOf()) : super() {
        mContacts = contacts
        mFavorites = favs
    }


    private val searchIntent = PublishSubject.create<String>()
    private val updateContactIntent = PublishSubject.create<Pair<Long, Boolean>>()

    private val loading: View by bindView(R.id.loading)
    private val recycler: RecyclerView by bindView(R.id.recycler)
    private val vSearch: SearchView by bindView(R.id.vSearch)
    private val queryListener: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            search(newText.toLowerCase())
            return false
        }
    }

    private var adapter: ContactsListAdapter? = null

    override fun onViewCreated(itemView: View) {
        initAdapter()
        initRecycler()
    }

    private fun initRecycler() {
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
    }

    private fun initAdapter() {
        if (adapter == null) {
            adapter = ContactsListAdapter(activity as Context,
                    mutableListOf(), hashSetOf())
            adapter!!.onItemClick = {
                openDetailedContact(it)
            }
            adapter!!.onFavoriteClick = { id, favorite ->
                updateContactIntent.onNext(Pair(id, favorite.not()))
            }
            //if adapter is null, get initial data
            render(ContactsSearchVS.DataState(Pair(mContacts, mFavorites)))
        }
    }

    override fun getLayoutId() = R.layout.screen_search

    override fun initPresenter() = DaggerContactsSearchComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .contactsSearchModule(ContactsSearchModule(mContacts, mFavorites))
            .build()
            .presenter()

    override fun render(state: BaseViewState) {
        when (state) {
            is ContactsSearchVS.LoadingState -> {
                //show loading only if adapter is empty
                if ((adapter?.itemCount ?: 0) == 0) {
                    loading.visibility = View.VISIBLE
                }
            }
            is ContactsSearchVS.ErrorState -> {
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
            is ContactsSearchVS.DataState -> {
                loading.visibility = View.GONE
                adapter!!.updateData(state.data.first)
                adapter!!.favorites = state.data.second
            }
            is ContactsSearchVS.UpdateContactState -> {
                loading.visibility = View.GONE
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

    override fun search(query: String) {
        searchIntent.onNext(query)
    }

    override fun setRetainMode() {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        vSearch.setOnQueryTextListener(queryListener)
    }

    override fun getSearchIntent() = searchIntent

    override fun updateContactIntent() = updateContactIntent

}