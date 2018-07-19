package ru.wearemad.cleanarcexm.presentation.mvi.contactssearch

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface ContactsSearchView : BaseView {

    fun getSearchIntent(): Observable<String>

    fun search(query: String)

    fun openDetailedContact(id: Long)
}