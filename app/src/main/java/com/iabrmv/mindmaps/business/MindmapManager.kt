package com.iabrmv.mindmaps.business

import com.iabrmv.mindmaps.business.model.Mindmap
import com.iabrmv.mindmaps.database.*
import io.realm.Realm
import io.realm.kotlin.where

class MindmapManager(val realm: Realm) {

    fun loadMindmap(id: Int) = realm
        .where<MindmapEntity>()
        .equalTo("id", id)
        .findFirst()?.toBusinessModel()

    fun loadMindmaps(): List<Mindmap> = realm
        .where<MindmapEntity>()
        .findAll()
        .map {
            it.toBusinessModel()
        }

    fun saveMindmap(mindmap: Mindmap) {
        realm.executeTransaction { realm ->
            realm.copyToRealmOrUpdate(mindmap.toEntity())
        }
    }


    fun clear() {
        realm.executeTransaction { realm ->
            realm.deleteAll()
        }
    }
}