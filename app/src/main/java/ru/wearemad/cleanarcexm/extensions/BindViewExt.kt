package ru.wearemad.cleanarcexm.extensions

import android.app.Activity
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.bluelinelabs.conductor.Controller

fun <T : View?> View.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafelazy { findViewById<T>(idRes) as T }
}

fun <T : View?> Controller.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafelazy { view!!.findViewById<T>(idRes) as T }
}

fun <T : View?> Activity.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafelazy { findViewById<T>(idRes) as T }
}

fun <T : View?> RecyclerView.ViewHolder.bind(@IdRes idRes: Int): Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return unsafelazy { itemView!!.findViewById<T>(idRes) as T }
}


private fun <T> unsafelazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)