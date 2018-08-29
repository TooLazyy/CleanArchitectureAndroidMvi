package ru.wearemad.cleanarcexm.domain.global.repositories

import io.reactivex.Observable
import ru.wearemad.cleanarcexm.domain.global.models.Contact

interface FavoritesRepository {

    fun updateContactFavorite(id: Long, isFavorite: Boolean): Observable<Unit>

    fun getFavoritesList(): Observable<HashSet<Long>>

    fun getFavoriteUsers(): Observable<List<Contact>>
}