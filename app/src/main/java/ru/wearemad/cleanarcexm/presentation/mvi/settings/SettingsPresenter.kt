package ru.wearemad.cleanarcexm.presentation.mvi.settings

import com.hannesdorfmann.mosby3.mvi.MviBasePresenter
import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import javax.inject.Inject

@Screen
class SettingsPresenter
@Inject constructor()
    : MviBasePresenter<SettingsView, SettingsVS>() {

    override fun bindIntents() {}
}