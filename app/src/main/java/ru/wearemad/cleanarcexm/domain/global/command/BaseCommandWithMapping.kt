package ru.wearemad.cleanarcexm.domain.global.command

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.wearemad.cleanarcexm.extensions.applyObservableAsync
import ru.wearemad.cleanarcexm.extensions.applyObservableCompute

abstract class BaseCommandWithMapping<RESPONSE, RESULT> {

    protected var subscribeScheduler = Schedulers.io()

    protected abstract fun serviceRequest(): Observable<RESPONSE>

    protected abstract fun afterResponse(result: Observable<RESPONSE>): Observable<RESULT>

    fun execute(): Observable<RESULT> {
        val result = serviceRequest()
                .applyObservableAsync(subscribeScheduler)
        return afterResponse(result).applyObservableCompute()
    }
}