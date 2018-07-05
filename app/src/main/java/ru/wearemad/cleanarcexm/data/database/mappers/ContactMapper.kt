package ru.wearemad.cleanarcexm.data.database.mappers

import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity
import ru.wearemad.cleanarcexm.domain.global.mappers.BaseMapper
import ru.wearemad.cleanarcexm.domain.global.models.Contact

class ContactMapper : BaseMapper<ContactEntity, Contact> {

    override fun fromEntity(from: ContactEntity) =
            Contact(from.id, from.name, from.surname, from.phone, from.photo)

    override fun toEntity(from: Contact) =
            ContactEntity(from.id, from.name, from.surname, from.phone, from.photo)
}