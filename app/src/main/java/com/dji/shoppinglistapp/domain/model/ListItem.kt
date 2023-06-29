package com.dji.shoppinglistapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItem(
    val name: String,
    val isChecked: Boolean = false,
    @PrimaryKey
    val id: Int = 0
)
