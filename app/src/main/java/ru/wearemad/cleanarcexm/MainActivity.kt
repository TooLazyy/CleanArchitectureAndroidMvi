package ru.wearemad.cleanarcexm

import android.os.Bundle
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler
import ru.wearemad.cleanarcexm.presentation.base.BaseActivity
import ru.wearemad.cleanarcexm.presentation.screens.main.MainController

class MainActivity : BaseActivity() {

    private lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<FrameLayout>(R.id.fContent)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        if(router.hasRootController().not()) {
            router.setRoot(RouterTransaction.with(MainController()).tag(MainController.TAG)
                    .pushChangeHandler(FadeChangeHandler()))
        }
    }

    override fun onBackPressed() {
        if (router.handleBack().not()) {
            super.onBackPressed()
        }
    }
}