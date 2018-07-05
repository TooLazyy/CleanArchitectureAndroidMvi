package ru.wearemad.cleanarcexm.data.database.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.wearemad.cleanarcexm.data.database.converters.ContactListConverter
import ru.wearemad.cleanarcexm.data.database.dao.ContactsDao
import ru.wearemad.cleanarcexm.data.database.entity.ContactDetailsEntity
import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity

const val DB_NAME = "app-db"

@Database(entities = [(ContactEntity::class), (ContactDetailsEntity::class)],
        version = 1, exportSchema = false)
@TypeConverters(
        ContactListConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userContactsDao(): ContactsDao
}