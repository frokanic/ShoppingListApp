package com.dji.shoppinglistapp.domain.use_case

data class ShoppingListUseCases(
    val insertItemUseCase: InsertItemUseCase,
    val updateItemUseCase: UpdateItemUseCase
)