package ru.wearemad.cleanarcexm.presentation.mvi.global

import com.hannesdorfmann.mosby3.mvp.MvpView

interface BaseView : MvpView {

    fun render(state: BaseViewState)
}