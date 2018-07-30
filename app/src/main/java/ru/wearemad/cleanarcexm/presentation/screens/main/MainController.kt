package ru.wearemad.cleanarcexm.presentation.screens.main

import android.view.View
import android.view.ViewGroup
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ru.wearemad.cleanarcexm.MyApp
import ru.wearemad.cleanarcexm.R
import ru.wearemad.cleanarcexm.di.main.DaggerMainComponent
import ru.wearemad.cleanarcexm.extensions.bindView
import ru.wearemad.cleanarcexm.presentation.base.NavigationHelper
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseController
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.presentation.mvi.main.MainPresenter
import ru.wearemad.cleanarcexm.presentation.mvi.main.MainVS
import ru.wearemad.cleanarcexm.presentation.mvi.main.MainView
import ru.wearemad.cleanarcexm.presentation.screens.contactslist.ContactsController
import ru.wearemad.cleanarcexm.presentation.screens.favorites.FavoritesController
import ru.wearemad.cleanarcexm.presentation.screens.settings.SettingsController

class MainController : BaseController<MainView, MainPresenter, MainVS>(),
        MainView,
        AHBottomNavigation.OnTabSelectedListener {

    private val sfContent by bindView<ViewGroup>(R.id.sfContent)
    private val vNav by bindView<AHBottomNavigation>(R.id.vNav)
    private var mCurrentPosition = 0
    private lateinit var mChildRouter: Router

    companion object {
        const val TAG = "MainController"
    }

    override fun onViewCreated(itemView: View) {
        mChildRouter = getChildRouter(sfContent)
        initNavigation()
        if (!mChildRouter.hasRootController()) {
            mChildRouter.setRoot(RouterTransaction.with(ContactsController())
                    .tag(ContactsController.TAG)
                    .popChangeHandler(FadeChangeHandler())
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    override fun getLayoutId() = R.layout.screen_main

    override fun initPresenter() = DaggerMainComponent.builder()
            .applicationComponent(MyApp.appComponent)
            .build()
            .presenter()

    override fun render(state: BaseViewState) {}

    private fun initNavigation() {
        vNav.setOnTabSelectedListener(this)
        val adapter = AHBottomNavigationAdapter(activity, R.menu.menu_main)
        adapter.setupWithBottomNavigation(vNav)
    }

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        if (position == mCurrentPosition) {
            return false
        }
        mCurrentPosition = position
        changeScreen()
        return true
    }

    private fun changeScreen() {
        when (mCurrentPosition) {
            0 -> {
                NavigationHelper.handleRouting(
                        mChildRouter,
                        ContactsController.TAG,
                        {
                            mChildRouter.pushController(
                                    RouterTransaction.with(ContactsController())
                                            .tag(ContactsController.TAG)
                                            .pushChangeHandler(FadeChangeHandler())
                                            .popChangeHandler(FadeChangeHandler())
                            )
                        }
                )
            }
            1 -> {
                NavigationHelper.handleRouting(
                        mChildRouter,
                        FavoritesController.TAG,
                        {
                            mChildRouter.pushController(
                                    RouterTransaction.with(FavoritesController())
                                            .tag(FavoritesController.TAG)
                                            .pushChangeHandler(FadeChangeHandler())
                                            .popChangeHandler(FadeChangeHandler()))
                        }
                )
            }
            2 -> {
                NavigationHelper.handleRouting(
                        mChildRouter,
                        SettingsController.TAG,
                        {
                            mChildRouter.pushController(
                                    RouterTransaction.with(SettingsController())
                                            .tag(SettingsController.TAG)
                                            .pushChangeHandler(FadeChangeHandler())
                                            .popChangeHandler(FadeChangeHandler()))
                        }
                )
            }
        }
    }

    override fun setRetainMode() {
        retainViewMode = RetainViewMode.RETAIN_DETACH
    }
}