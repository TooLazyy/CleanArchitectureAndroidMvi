package ru.wearemad.cleanarcexm.domain.favorites

import ru.wearemad.cleanarcexm.di.global.scopes.Screen
import ru.wearemad.cleanarcexm.domain.global.repositories.FavoritesRepository
import javax.inject.Inject

@Screen
class FavoritesInteractor
@Inject constructor(
        private val repository: FavoritesRepository
) {
    fun getFavoriteUsers() = repository.getFavoriteUsers()

    fun updateContactFavorite(id: Long, isFavorite: Boolean) =
            repository.updateContactFavorite(id, isFavorite)

    fun getFavorites() = repository.getFavoritesList()
}