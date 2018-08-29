package ru.wearemad.cleanarcexm.presentation.screens.favorites

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.favorites.DaggerFavoritesComponent
import ru.wearemad.cleanarcexm.extensions.bindView
import ru.wearemad.cleanarcexm.presentation.changehandlers.CircularRevealChangeHandlerCompatJ
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesVS
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.screens.contactslist.ContactsListAdapter
import ru.wearemad.cleanarcexm.presentation.screens.contactssearch.ContactsSearchController

class FavoritesController : BaseController<FavoritesView, FavoritesPresenter, FavoritesVS>(), FavoritesView {

    private val refreshContactsIntent = PublishSubject.create<Unit>()
    private val updateContactIntent = PublishSubject.create<Pair<Long, Boolean>>()
    private val updateFavoritesIntent = PublishSubject.create<Unit>()

    private val ivSearch: View by bindView(R.id.ivSearch)
    private val loading: View by bindView(R.id.loading)
    private val recycler: RecyclerView by bindView(R.id.recycler)
    private val root: ViewGroup by bindView(R.id.favorites_root)

    private var adapter: ContactsListAdapter? = null

    override fun getLayoutId() = R.layout.screen_favorites

    override fun initPresenter() = DaggerFavoritesComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .build()
            .presenter()

    companion object {
        const val TAG = "FavoritesController"
    }

    override fun onViewCreated(itemView: View) {
        refreshContactsIntent.onNext(Unit)
        ivSearch.setOnClickListener {
            getParentRouter()?.pushController(
                    RouterTransaction.with(
                            ContactsSearchController(
                                    adapter?.data ?: listOf(),
                                    adapter?.favorites ?: hashSetOf())
                    ).tag(ContactsSearchController.TAG)
                            .pushChangeHandler(CircularRevealChangeHandlerCompatJ(ivSearch, root))
                            .popChangeHandler(CircularRevealChangeHandlerCompatJ(ivSearch, root))
            )
        }
    }

    override fun render(state: BaseViewState) {
        when (state) {
            is FavoritesVS.LoadingState -> {
                //show loading only if adapter is empty
                if ((adapter?.itemCount ?: 0) == 0) {
                    loading.visibility = View.VISIBLE
                }
            }
            is FavoritesVS.ErrorState -> {
                loading.visibility = View.GONE
                Toast.makeText(activity, state.error, Toast.LENGTH_SHORT).show()
            }
            is FavoritesVS.DataState -> {
                loading.visibility = View.GONE
                if (adapter == null || recycler.adapter == null) {
                    adapter = ContactsListAdapter(activity as Context,
                            state.data.toMutableList(),
                            hashSetOf())
                    recycler.layoutManager = LinearLayoutManager(activity)
                    recycler.adapter = adapter
                    adapter!!.onItemClick = {
                        openDetailedContact(it)
                    }
                    adapter!!.onFavoriteClick = { id, favorite ->
                        updateContactIntent.onNext(Pair(id, favorite.not()))
                    }
                } else {
                    adapter!!.updateData(state.data)
                }
            }
            is FavoritesVS.UpdateContactState -> {
                loading.visibility = View.GONE
            }
            is FavoritesVS.UpdateFavoritesState -> {
                adapter?.favorites = state.favorites
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun loadContactsIntent() = refreshContactsIntent

    override fun openDetailedContact(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun updateContactIntent() = updateContactIntent

    override fun updateFavoritesIntent() = updateFavoritesIntent
}