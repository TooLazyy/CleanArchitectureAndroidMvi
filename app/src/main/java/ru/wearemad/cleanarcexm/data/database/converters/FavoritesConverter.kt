package ru.wearemad.cleanarcexm.data.database.converters

import com.google.gson.reflect.TypeToken
import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity

class FavoritesConverter : BaseConverter<MutableList<@JvmSuppressWildcards Long>>() {

    override fun getEmptyInstance() = mutableListOf<@JvmSuppressWildcards Long>()

    override fun getTypeToken() = object : TypeToken<MutableList<@JvmSuppressWildcards Long>>(){}.type
}