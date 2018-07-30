package ru.wearemad.cleanarcexm.presentation.screens.favorites

import android.view.View
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.favorites.DaggerFavoritesComponent
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesVS
import ru.wearemad.cleanarcexm.presentation.mvi.favorites.FavoritesView
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

class FavoritesController : BaseController<FavoritesView, FavoritesPresenter, FavoritesVS>(), FavoritesView {

    override fun onViewCreated(itemView: View) {}

    override fun getLayoutId() = R.layout.screen_favorites

    override fun initPresenter() = DaggerFavoritesComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .build()
            .presenter()

    companion object {
        const val TAG = "FavoritesController"
    }

    override fun render(state: BaseViewState) {}

}