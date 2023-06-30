package com.dji.shoppinglistapp.presentation.shopping_list_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dji.shoppinglistapp.databinding.ItemListitemBinding
import com.dji.shoppinglistapp.domain.model.ListItem

class ShoppingListAdapter(
    private val editHandler: (item: ListItem) -> Unit,
    private val deleteHandler: (item: ListItem) -> Unit
): ListAdapter<ListItem, ShoppingListAdapter.ViewHolder>(ListItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemListitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem) {
            binding.itemName.text = "${absoluteAdapterPosition + 1}. ${item.name}"

            binding.editSymbol.setOnClickListener {
                editHandler(item)
            }

            binding.deleteSymbol.setOnClickListener {
                deleteHandler(item)
            }
        }
    }

    class ListItemDiffCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }
}
