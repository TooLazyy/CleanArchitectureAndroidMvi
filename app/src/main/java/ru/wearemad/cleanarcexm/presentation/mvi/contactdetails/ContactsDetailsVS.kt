package ru.wearemad.cleanarcexm.presentation.mvi.contactdetails

import ru.wearemad.cleanarcexm.domain.global.models.ContactDetails
import ru.wearemad.cleanarcexm.presentation.mvi.global.BaseViewState

sealed class ContactsDetailsVS : BaseViewState {

    object LoadingState : ContactsDetailsVS()

    data class DataState(
            val contact: ContactDetails
    ) : ContactsDetailsVS()

    data class ErrorState(
            val error: String
    ) : ContactsDetailsVS()
}