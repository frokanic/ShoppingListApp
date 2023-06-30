package com.dji.shoppinglistapp.presentation.shopping_list_screen

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dji.shoppinglistapp.databinding.ItemListitemBinding
import com.dji.shoppinglistapp.domain.model.ListItem

class ShoppingListAdapter(
    private val editHandler: (item: ListItem) -> Unit,
    private val deleteHandler: (item: ListItem) -> Unit
): RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

    private var listItems: MutableList<ListItem> = mutableListOf()

    fun submitList(items: List<ListItem>) {
        listItems.clear()
        listItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listItems[position]
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ItemListitemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem) {
            binding.itemName.text = "${layoutPosition + 1}. ${item.name}"

            binding.checkBox.setOnCheckedChangeListener(null)
            binding.checkBox.isChecked = item.isChecked

            if (item.isChecked) {
                binding.itemName.paintFlags = binding.itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.itemName.paintFlags = binding.itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
                item.isChecked = isChecked
                if (isChecked) {
                    binding.itemName.paintFlags = binding.itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    binding.itemName.paintFlags = binding.itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                editHandler(item)
            }

            binding.editSymbol.setOnClickListener {
                editHandler(item)
            }

            binding.deleteSymbol.setOnClickListener {
                deleteHandler(item)
                listItems.remove(item)
                notifyDataSetChanged()
            }
        }
    }

}
