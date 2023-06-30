package com.dji.shoppinglistapp.data.repository

import com.dji.shoppinglistapp.data.local.ListItemDao
import com.dji.shoppinglistapp.domain.model.ListItem
import com.dji.shoppinglistapp.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow

class ShoppingRepositoryImpl(
    private val dao: ListItemDao
): ShoppingRepository {

    override suspend fun insertItem(item: ListItem) {
        dao.insertItem(item)
    }

    override suspend fun updateItem(item: ListItem) {
        dao.updateItem(item)
    }

    override suspend fun deleteItem(item: ListItem) {
        dao.deleteItem(item)
    }

    override suspend fun deleteAllItems() {
        dao.deleteAllItems()
    }

    override fun getAllItems(): Flow<List<ListItem>> {
        return dao.getAllItems()
    }

    override fun getItemCount(): Flow<Int> {
        return dao.getItemCount()
    }

}