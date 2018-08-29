package ru.wearemad.cleanarcexm.data.database.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.wearemad.cleanarcexm.data.database.converters.ContactListConverter
import ru.wearemad.cleanarcexm.data.database.converters.FavoritesConverter
import ru.wearemad.cleanarcexm.data.database.dao.ContactsDao
import ru.wearemad.cleanarcexm.data.database.dao.FavoritesDao
import ru.wearemad.cleanarcexm.data.database.entity.ContactDetailsEntity
import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity
import ru.wearemad.cleanarcexm.data.database.entity.FavoritesEntity

const val DB_NAME = "app-db"

@Database(entities = [(ContactEntity::class), (ContactDetailsEntity::class), (FavoritesEntity::class)],
        version = 1, exportSchema = false)
@TypeConverters(
        ContactListConverter::class,
        FavoritesConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userContactsDao(): ContactsDao

    abstract fun favoritesDao(): FavoritesDao
}