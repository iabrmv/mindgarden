package com.iabrmv.mindmaps

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MindGardenApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeRealm()
    }

    private fun initializeRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)
    }
}