package ru.wearemad.cleanarcexm.presentation.mvi.contactslist

import ru.wearemad.cleanarcexm.domain.global.models.Contact
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

sealed class ContactsListVS : BaseViewState {

    object LoadingState : ContactsListVS()

    data class DataState(
            val data: Pair<List<Contact>, HashSet<Long>>
    ) : ContactsListVS()

    object UpdateContactState : ContactsListVS()

    data class UpdateFavoritesState(
            val favorites: HashSet<Long>
    ) : ContactsListVS()

    data class ErrorState(
            val error: String
    ) : ContactsListVS()
}