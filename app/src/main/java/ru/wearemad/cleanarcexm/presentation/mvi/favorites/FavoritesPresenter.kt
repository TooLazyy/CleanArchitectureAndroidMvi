package ru.wearemad.cleanarcexm.presentation.mvi.favorites

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.favorites.FavoritesInteractor
import ru.wearemad.cleanarcexm.presentation.mvi.contactslist.ContactsListView
import javax.inject.Inject

@Screen
class FavoritesPresenter
@Inject constructor(
        private val interactor: FavoritesInteractor
) : MviBasePresenter<FavoritesView, FavoritesVS>() {

    override fun bindIntents() {
        val state1: Observable<FavoritesVS> =
                intent(FavoritesView::loadContactsIntent)
                        .flatMap {
                            interactor.getFavoriteUsers()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        FavoritesVS.DataState(it)
                                    }
                                    .cast(FavoritesVS::class.java)
                                    .startWith(FavoritesVS.LoadingState)
                                    .onErrorReturn {
                                        FavoritesVS.ErrorState(it.message ?: "")
                                    }
                        }

        val state2: Observable<FavoritesVS> =
                intent(FavoritesView::updateContactIntent)
                        .flatMap {
                            interactor.updateContactFavorite(it.first, it.second)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        FavoritesVS.UpdateContactState
                                    }
                                    .cast(FavoritesVS::class.java)
                                    .startWith(FavoritesVS.LoadingState)
                                    .onErrorReturn {
                                        FavoritesVS.ErrorState(it.message ?: "")
                                    }
                        }

        val state3: Observable<FavoritesVS> =
                intent(FavoritesView::updateFavoritesIntent)
                        .flatMap {
                            interactor.getFavorites()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        FavoritesVS.UpdateFavoritesState(it)
                                    }
                                    .cast(FavoritesVS::class.java)
                                    .onErrorReturn {
                                        FavoritesVS.ErrorState(it.message ?: "")
                                    }
                        }

        val state4: Observable<FavoritesVS> =
                intent(FavoritesView::openSearchIntent)
                        .flatMap {
                            interactor.getFavorites()
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .map {
                                        FavoritesVS.OpenSearchState(it)
                                    }
                                    .cast(FavoritesVS::class.java)
                                    .onErrorReturn {
                                        FavoritesVS.ErrorState(it.message ?: "")
                                    }
                        }
        val state = Observable.merge(
                state1,
                state2,
                state3,
                state4)

        subscribeViewState(state, FavoritesView::render)
    }
}