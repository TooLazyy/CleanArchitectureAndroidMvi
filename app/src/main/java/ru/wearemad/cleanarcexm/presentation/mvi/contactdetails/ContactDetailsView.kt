package ru.wearemad.cleanarcexm.presentation.mvi.contactdetails

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseView

interface ContactDetailsView : BaseView {

    fun loadDetailIntent() : Observable<Long>
}