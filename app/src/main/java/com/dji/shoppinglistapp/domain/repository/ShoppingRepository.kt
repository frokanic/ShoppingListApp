package com.dji.shoppinglistapp.domain.repository

import com.dji.shoppinglistapp.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {

    suspend fun insertItem(item: ListItem)

    suspend fun updateItem(item: ListItem)

    suspend fun deleteItem(item: ListItem)

    suspend fun deleteAllItems()

    fun getAllItems(): Flow<List<ListItem>>

}