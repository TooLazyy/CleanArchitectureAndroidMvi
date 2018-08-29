package ru.wearemad.cleanarcexm.data.database.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.*
import ru.wearemad.cleanarcexm.data.database.entity.FavoritesEntity

@Dao
abstract class FavoritesDao {

    /**
     * contact favorite
     */

    @Query("SELECT * FROM ${FavoritesEntity.TABLE_NAME}")
    abstract fun getFavorites(): List<FavoritesEntity>

    @Insert(onConflict = REPLACE)
    abstract fun insertFavorites(list: FavoritesEntity)

    @Query("DELETE FROM ${FavoritesEntity.TABLE_NAME}")
    abstract fun deleteFavorites()

    @Transaction
    open fun updateFavorites(item: FavoritesEntity) {
        deleteFavorites()
        insertFavorites(item)
    }
}