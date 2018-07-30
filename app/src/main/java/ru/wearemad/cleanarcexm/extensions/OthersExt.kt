package ru.wearemad.cleanarcexm.extensions

import android.util.Log

fun <E> MutableList<E>.printCollection(tag: String = "MIINE") {
    forEach {
        Log.d(tag, it.toString())
    }
}