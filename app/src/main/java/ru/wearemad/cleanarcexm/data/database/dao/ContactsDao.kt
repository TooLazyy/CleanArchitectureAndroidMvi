package ru.wearemad.cleanarcexm.data.database.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.*
import ru.wearemad.cleanarcexm.data.database.entity.ContactDetailsEntity
import ru.wearemad.cleanarcexm.data.database.entity.ContactEntity

@Dao
abstract class ContactsDao {

    /**
     * contacts list cache
     */

    @Query("SELECT * FROM ${ContactEntity.TABLE_NAME}")
    abstract fun getContactsList(): List<ContactEntity>

    @Query("DELETE FROM ${ContactEntity.TABLE_NAME}")
    abstract fun deleteContactsList()

    @Insert(onConflict = REPLACE)
    abstract fun insertContactsList(contacts: List<ContactEntity>)

    @Transaction
    open fun updateContactsList(contacts: List<ContactEntity>) {
        deleteContactsList()
        insertContactsList(contacts)
    }

    /**
     * contact details cache
     */

    @Query("SELECT * FROM ${ContactDetailsEntity.TABLE_NAME} " +
            " WHERE ${ContactDetailsEntity.COLUMN_ID} = :contactId")
    abstract fun getContactDetails(contactId: Long): List<ContactDetailsEntity>

    @Query("DELETE FROM ${ContactDetailsEntity.TABLE_NAME}" +
            " WHERE ${ContactDetailsEntity.COLUMN_ID} = :contactId")
    abstract fun deleteContactDetails(contactId: Long)

    @Insert(onConflict = REPLACE)
    abstract fun insertContactDetails(contact: ContactDetailsEntity)

    @Transaction
    open fun updateContactDetails(contact: ContactDetailsEntity) {
        deleteContactDetails(contact.id)
        insertContactDetails(contact)
    }

}