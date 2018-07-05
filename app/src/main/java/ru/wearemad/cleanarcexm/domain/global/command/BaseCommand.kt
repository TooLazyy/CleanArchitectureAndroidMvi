package ru.wearemad.cleanarcexm.domain.global.command

import io.reactivex.Observable

abstract class BaseCommand<RESULT> : BaseCommandWithMapping<RESULT, RESULT>() {

    override fun afterResponse(result: Observable<RESULT>): Observable<RESULT> = result
}