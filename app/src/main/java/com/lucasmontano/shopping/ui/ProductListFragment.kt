package com.lucasmontano.shopping.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasmontano.shopping.R
import com.lucasmontano.shopping.adapters.DelegationAdapter
import com.lucasmontano.shopping.databinding.FragmentProductListBinding
import com.lucasmontano.shopping.ui.adapters.ProductAdapterDelegate
import com.lucasmontano.shopping.ui.adapters.TileProductAdapterDelegate
import com.lucasmontano.shopping.ui.models.ProductUiModel
import com.lucasmontano.shopping.viewmodels.ProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private val viewModel: ProductListViewModel by viewModels()

    private lateinit var binding: FragmentProductListBinding

    private val itemsDiff = object : DiffUtil.ItemCallback<ProductUiModel>() {
        override fun areItemsTheSame(
            oldItem: ProductUiModel,
            newItem: ProductUiModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ProductUiModel,
            newItem: ProductUiModel
        ): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.lifecycleOwner = viewLifecycleOwner

        val delegationAdapter = DelegationAdapter(
            itemsDiff,
            TileProductAdapterDelegate(viewLifecycleOwner) {

            },
            ProductAdapterDelegate(viewLifecycleOwner) {

            }
        )
        binding.productsRecyclerView.adapter = delegationAdapter
        binding.productsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        subscribeUi(delegationAdapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_product_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_type -> {
                // TODO filter by type
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: DelegationAdapter<ProductUiModel>) {
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            adapter.setData(uiState.products)
        }
    }
}
