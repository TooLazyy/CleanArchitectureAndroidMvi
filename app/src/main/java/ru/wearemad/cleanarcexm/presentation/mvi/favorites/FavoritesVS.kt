package ru.wearemad.cleanarcexm.presentation.mvi.favorites

import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.domain.global.models.Contact

sealed class FavoritesVS : BaseViewState {
    object LoadingState : FavoritesVS()

    data class DataState(
            val data: List<Contact>
    ) : FavoritesVS()

    object UpdateContactState : FavoritesVS()

    data class OpenSearchState(
            val favorites: HashSet<Long>
    ) : FavoritesVS()

    data class UpdateFavoritesState(
            val favorites: HashSet<Long>
    ) : FavoritesVS()

    data class ErrorState(
            val error: String
    ) : FavoritesVS()
}