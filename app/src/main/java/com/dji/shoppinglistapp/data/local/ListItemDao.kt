package com.dji.shoppinglistapp.data.local

import androidx.room.*
import com.dji.shoppinglistapp.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ListItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ListItem)

    @Update
    suspend fun updateItem(item: ListItem)

    @Delete
    suspend fun deleteItem(item: ListItem)

    @Query("DELETE FROM ListItem")
    suspend fun deleteAllItems()

    @Query("SELECT * FROM ListItem")
    fun getAllItems(): Flow<List<ListItem>>

    @Query("SELECT COUNT(*) FROM ListItem")
    fun getItemCount(): Flow<Int>

}