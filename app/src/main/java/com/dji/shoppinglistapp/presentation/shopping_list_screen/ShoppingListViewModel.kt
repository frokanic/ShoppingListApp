package com.dji.shoppinglistapp.presentation.shopping_list_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dji.shoppinglistapp.domain.interactor.ShoppingListInteractor
import com.dji.shoppinglistapp.domain.model.ListItem
import com.dji.shoppinglistapp.domain.use_case.ShoppingListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingListUseCases: ShoppingListUseCases,
    private val shoppingListInteractor: ShoppingListInteractor
) : ViewModel() {

    val listItems: LiveData<List<ListItem>> = liveData {
        emitSource(shoppingListInteractor.getAllItems().asLiveData())
    }

    val errorMessage = MutableLiveData<String>()

    fun insertItem(item: ListItem) {
        viewModelScope.launch {
            val result = shoppingListUseCases.insertItemUseCase(item)
            if (!result.isSuccess) {
                errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun updateItem(item: ListItem) {
        viewModelScope.launch {
            val result = shoppingListUseCases.updateItemUseCase(item)
            if (!result.isSuccess) {
                errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }

    fun deleteItem(item: ListItem) {
        viewModelScope.launch {
            shoppingListInteractor.deleteItem(item)
        }
    }

    fun deleteAllItems() {
        viewModelScope.launch {
            shoppingListInteractor.deleteAllItems()
        }
    }
}
