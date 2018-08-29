package ru.wearemad.cleanarcexm.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = FavoritesEntity.TABLE_NAME)
@JvmSuppressWildcards
data class FavoritesEntity(
        @ColumnInfo(name = "favorites")
        val favorites: MutableList<Long>
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L

    companion object {
        const val TABLE_NAME = "favorites_table"
    }
}