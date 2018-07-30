package ru.wearemad.cleanarcexm.presentation.mvi.main

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import javax.inject.Inject

@Screen
class MainPresenter
@Inject constructor() : MviBasePresenter<MainView, MainVS>() {

    override fun bindIntents() {}
}