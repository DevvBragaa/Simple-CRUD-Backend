package com.dev.crud.mapper

interface Mapper<T, U> {

    fun map(t: T): U
}