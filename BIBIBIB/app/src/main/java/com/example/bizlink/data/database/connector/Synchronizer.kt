package com.example.bizlink.data.database.connector

import android.content.Context
import androidx.room.Room
import com.example.bizlink.data.database.Database

object Synchronizer {
    private var dbInstance: Database? = null

    fun initData(ctx: Context) {
        dbInstance = Room.databaseBuilder(ctx, Database::class.java, "bizlinkApp.db")
            .build()

    }

    fun getDbInstance(): Database {
        return dbInstance ?: throw IllegalStateException("Db not initialized")
    }

}