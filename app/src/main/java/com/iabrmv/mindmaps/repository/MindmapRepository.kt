package com.iabrmv.mindmaps.repository

import com.iabrmv.mindmaps.data.business.Mindmap
import com.iabrmv.mindmaps.data.database.*
import io.realm.Realm
import io.realm.kotlin.where

class MindmapRepository(val realm: Realm) {

    fun loadMindmap(id: Int) = realm
        .where<MindmapEntity>()
        .equalTo("id", id)
        .findFirst()?.toBusinessModel()

    fun loadMindmapsFromDB(): List<Mindmap> = realm
        .where<MindmapEntity>()
        .findAll()
        .map { it.toBusinessModel() }
        .sortedByDescending { it.lastEditedTimeMillis }

    fun saveMindmapToDB(mindmap: Mindmap) {
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