package ru.wearemad.cleanarcexm.presentation.mvi.favorites

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface FavoritesView : BaseView {
    fun loadContactsIntent() : Observable<Unit>

    fun openDetailedContact(id: Long)

    fun updateContactIntent(): Observable<Pair<Long, Boolean>>

    fun updateFavoritesIntent(): Observable<Unit>

    fun openSearchIntent(): Observable<Unit>
}