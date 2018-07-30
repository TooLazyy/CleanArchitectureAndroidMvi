package ru.wearemad.cleanarcexm.presentation.base

import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

object NavigationHelper {

    fun handleRouting(router: Router, controllerTag: String,
                      loader: () -> Unit) {
        if (controllerTag.isEmpty()) {
            return
        }
        val controller = router.getControllerWithTag(controllerTag)
        if (controller == null) {
            //create instance
            //make transaction
            loader.invoke()
        } else {
            if (shuffleBackStack(router, controllerTag).not()) {
                loader.invoke()
            }
        }
    }

    private fun shuffleBackStack(router: Router, tag: String): Boolean {
        val backstack = router.backstack
        val position = getTransactionPosition(backstack, tag)
        if (position != -1) {
            val transaction = backstack.removeAt(position)
            if (transaction.controller().isDestroyed) {
                return false
            }
            backstack.add(0, transaction)
            backstack.reverse()
            router.setBackstack(backstack, null)
        }
        return true
    }

    private fun getTransactionPosition(
            backstack: MutableList<RouterTransaction>,
            tag: String) = backstack.indexOfFirst { it.tag() == tag}
}