package com.dji.shoppinglistapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListItem(
    val name: String,
    var isChecked: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
