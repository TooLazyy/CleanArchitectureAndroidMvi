package ru.wearemad.cleanarcexm.data.database.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import java.lang.reflect.Type

abstract class BaseConverter<T> {
    private val gson = Gson()

    @TypeConverter
    fun fromType(response: T): String {
        if (response == null) {
            return ""
        }
        val json = gson.toJson(response, getTypeToken())
        return json
    }

    @TypeConverter
    fun toType(response: String): T {
        if (response == null || response.isEmpty()) {
            return getEmptyInstance()
        }
        return gson.fromJson(response, getTypeToken())
    }

    abstract fun getEmptyInstance(): T

    abstract fun getTypeToken(): Type
}