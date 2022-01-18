package com.iabrmv.mindmaps.business.model

interface BusinessModel<M> {
    fun toEntity() : M
}