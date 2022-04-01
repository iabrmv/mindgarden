package com.iabrmv.mindmaps.data.business

interface BusinessModel<M> {
    fun toEntity() : M
}