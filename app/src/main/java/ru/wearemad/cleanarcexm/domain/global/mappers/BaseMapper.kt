package ru.wearemad.cleanarcexm.domain.global.mappers

/**
 * E - entity
 * M - model
 */
interface BaseMapper<E, M> {

    fun fromEntity(from: E): M

    fun toEntity(from: M): E
}