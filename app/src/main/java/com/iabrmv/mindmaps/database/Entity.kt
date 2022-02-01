package com.iabrmv.mindmaps.database

interface Entity<T> {
    fun toBusinessModel(): T
}