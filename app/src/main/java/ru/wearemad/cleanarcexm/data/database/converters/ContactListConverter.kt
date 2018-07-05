package ru.wearemad.cleanarcexm.data.database.converters

import com.google.gson.reflect.TypeToken
import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity

class ContactListConverter : BaseConverter<List<ContactEntity>>() {

    override fun getEmptyInstance() = listOf<ContactEntity>()

    override fun getTypeToken() = object : TypeToken<List<ContactEntity>>(){}.type
}