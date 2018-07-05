package ru.wearemad.cleanarcexm.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = ContactDetailsEntity.TABLE_NAME)
data class ContactDetailsEntity(
        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: Long,
        @ColumnInfo(name = "user_name")
        var name: String,
        @ColumnInfo(name = "user_surname")
        var surname: String,
        @ColumnInfo(name = "user_phone")
        var phone: String,
        @ColumnInfo(name = "user_photo")
        var photo: String,
        @ColumnInfo(name = "user_job")
        var job: String,
        @ColumnInfo(name = "user_address")
        var address: String
) {
    companion object {
        const val TABLE_NAME = "contact_details_table"
        const val COLUMN_ID = "id"
    }
}