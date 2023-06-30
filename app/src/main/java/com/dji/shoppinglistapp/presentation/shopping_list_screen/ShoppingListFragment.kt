package com.dji.shoppinglistapp.presentation.shopping_list_screen


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dji.shoppinglistapp.databinding.FragmentShoppingListBinding
import com.dji.shoppinglistapp.domain.model.ListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingListViewModel by viewModels()
    private lateinit var adapter: ShoppingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        subscribeToObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter(
            editHandler = { item -> viewModel.updateItem(item) },
            deleteHandler = { item -> viewModel.deleteItem(item) }
        )

        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter
    }

    private fun subscribeToObservers() {
        viewModel.listItems.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        viewModel.itemCount.observe(viewLifecycleOwner) { count ->
            binding.btnClear.isEnabled = count > 0
        }
    }

    private fun setupClickListeners() {
        binding.btnAdd.setOnClickListener {
            val itemName = binding.etItem.text.toString()
            if (itemName.isNotBlank()) {
                viewModel.insertItem(ListItem(name = itemName))
                binding.etItem.text.clear()
            }
        }

        binding.btnClear.setOnClickListener {
            val itemCount = viewModel.itemCount.value ?: 0
            if (itemCount > 0) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Confirm Clear")
                    .setMessage("Are you sure you want to clear all items?")
                    .setPositiveButton("Yes") { _, _ ->
                        viewModel.deleteAllItems()
                    }
                    .setNegativeButton("No", null)
                    .show()
            } else {
                Toast.makeText(requireContext(), "No items to clear", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}