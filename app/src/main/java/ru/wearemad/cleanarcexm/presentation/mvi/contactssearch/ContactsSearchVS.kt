package ru.wearemad.cleanarcexm.presentation.mvi.contactssearch

import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState
import ru.wearemad.cleanarcexm.domain.global.models.Contact

sealed class ContactsSearchVS : BaseViewState {

    object LoadingState : ContactsSearchVS()

    data class DataState(
            val contacts: List<Contact>
    ) : ContactsSearchVS()

    data class ErrorState(
            val error: String
    ) : ContactsSearchVS()
}