package ru.wearemad.cleanarcexm.data.database.mappers

import ru.wearemad.cleanarcexm.data.database.entity.ContactDetailsEntity
import ru.wearemad.cleanarcexm.domain.global.mappers.BaseMapper
import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails

class ContactDetailsMapper : BaseMapper<ContactDetailsEntity, ContactDetails> {

    override fun fromEntity(from: ContactDetailsEntity) =
            ContactDetails(from.id, from.name, from.surname,
                    from.phone, from.photo, from.job, from.address)

    override fun toEntity(from: ContactDetails) =
            ContactDetailsEntity(from.id, from.name, from.surname,
                    from.phone, from.photo, from.job, from.address)
}