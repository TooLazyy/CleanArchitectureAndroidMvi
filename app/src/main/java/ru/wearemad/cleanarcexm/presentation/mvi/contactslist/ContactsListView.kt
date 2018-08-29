package ru.wearemad.cleanarcexm.presentation.mvi.contactslist

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface ContactsListView : BaseView {

    fun loadContactsIntent() : Observable<Unit>

    fun openDetailedContact(id: Long)

    fun updateContactIntent(): Observable<Pair<Long, Boolean>>

    fun updateFavoritesIntent(): Observable<Unit>
}