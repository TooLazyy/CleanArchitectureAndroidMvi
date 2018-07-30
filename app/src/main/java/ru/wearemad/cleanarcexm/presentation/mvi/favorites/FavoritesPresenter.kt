package ru.wearemad.cleanarcexm.presentation.mvi.favorites

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import javax.inject.Inject

@Screen
class FavoritesPresenter
@Inject constructor()
    : MviBasePresenter<FavoritesView, FavoritesVS>() {

    override fun bindIntents() {}
}