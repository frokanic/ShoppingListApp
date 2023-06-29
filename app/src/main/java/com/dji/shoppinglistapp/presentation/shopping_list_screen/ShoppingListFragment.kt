package com.dji.shoppinglistapp.presentation.shopping_list_screen


import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentShoppingListBinding.bind(view)

        setupRecyclerView()
        subscribeToObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = ShoppingListAdapter()
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
            viewModel.deleteAllItems()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}