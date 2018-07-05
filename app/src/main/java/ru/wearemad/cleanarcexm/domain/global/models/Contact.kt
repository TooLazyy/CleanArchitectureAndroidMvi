package ru.wearemad.cleanarcexm.domain.global.models

data class Contact(
        val id: Long = 0,
        var name: String = "",
        var surname: String = "",
        var phone: String = "",
        var photo: String = ""
)