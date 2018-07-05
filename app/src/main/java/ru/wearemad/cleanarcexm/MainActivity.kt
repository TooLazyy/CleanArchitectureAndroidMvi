package ru.wearemad.cleanarcexm

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import ru.wearemad.cleanarcexm.presentation.base.BaseActivity
import ru.wearemad.cleanarcexm.presentation.screens.contactslist.ContactsController

class MainActivity : BaseActivity() {

    private lateinit var router: Router
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val container = findViewById<FrameLayout>(R.id.fContent)

        router = Conductor.attachRouter(this, container, savedInstanceState)

        if(router.hasRootController().not()) {
            router.setRoot(RouterTransaction.with(ContactsController()))
        }
    }
}