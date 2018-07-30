package ru.wearemad.cleanarcexm.presentation.screens.settings

import android.view.View
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.settings.DaggerSettingsComponent
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.mvi.settings.SettingsPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.settings.SettingsVS
import ru.wearemad.cleanarcexm.presentation.mvi.settings.SettingsView

class SettingsController : BaseController<SettingsView, SettingsPresenter, SettingsVS>(), SettingsView {

    override fun onViewCreated(itemView: View) {}

    override fun getLayoutId() = R.layout.screen_settings

    override fun initPresenter() = DaggerSettingsComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .build()
            .presenter()

    companion object {
        const val TAG = "SettingsController"
    }

    override fun render(state: BaseViewState) {}

}