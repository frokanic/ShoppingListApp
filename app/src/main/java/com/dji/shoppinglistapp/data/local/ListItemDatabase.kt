package com.dji.shoppinglistapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dji.shoppinglistapp.domain.model.ListItem

@Database(
    entities = [ListItem::class],
    version = 1,
    exportSchema = false
)
abstract class ListItemDatabase: RoomDatabase() {

    abstract val dao: ListItemDao

    companion object {
        const val DATABASE_NAME = "listItem_db"
    }
}