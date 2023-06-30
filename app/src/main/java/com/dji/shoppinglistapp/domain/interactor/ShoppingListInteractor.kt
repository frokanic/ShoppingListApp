package com.dji.shoppinglistapp.domain.interactor

import com.dji.shoppinglistapp.domain.model.ListItem
import com.dji.shoppinglistapp.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow

class ShoppingListInteractor(
    private val repository: ShoppingRepository
) {

    suspend fun deleteItem(item: ListItem) {
        repository.deleteItem(item)
    }

    suspend fun deleteAllItems() {
        repository.deleteAllItems()
    }

    fun getAllItems(): Flow<List<ListItem>> {
        return repository.getAllItems()
    }

    fun getItemCount(): Flow<Int> {
        return repository.getItemCount()
    }

}
