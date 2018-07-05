package ru.wearemad.cleanarcexm.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = ContactEntity.TABLE_NAME)
data class ContactEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Long,
        @ColumnInfo(name = "user_name")
        val name: String,
        @ColumnInfo(name = "user_surname")
        val surname: String,
        @ColumnInfo(name = "user_phone")
        val phone: String,
        @ColumnInfo(name = "user_photo")
        val photo: String
) {
    companion object {
        const val TABLE_NAME = "contact_table"
    }
}