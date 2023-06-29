package com.dji.shoppinglistapp.domain.use_case

import com.dji.shoppinglistapp.domain.model.ListItem
import com.dji.shoppinglistapp.domain.repository.ShoppingRepository


class InsertItemUseCase(
    private val repository: ShoppingRepository
) {

    suspend operator fun invoke(item: ListItem): Result<Unit> {
        if (item.name.isNullOrBlank()) {
            return Result.failure(IllegalArgumentException("Item name cannot be null or blank"))
        }

        val trimmedItem = item.copy(name = item.name.trim())

        return try {
            repository.insertItem(trimmedItem)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}