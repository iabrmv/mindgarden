package com.iabrmv.mindmaps.data.database

interface Entity<T> {
    fun toBusinessModel(): T
}