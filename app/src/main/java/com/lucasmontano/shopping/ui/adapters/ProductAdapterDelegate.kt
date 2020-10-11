package com.lucasmontano.shopping.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.lucasmontano.shopping.R
import com.lucasmontano.shopping.databinding.ItemBasicProductBinding
import com.lucasmontano.shopping.ui.models.BasicProductUiModel
import com.lucasmontano.shopping.ui.models.ProductUiModel

internal class ProductAdapterDelegate(
    private val viewLifecycleOwner: LifecycleOwner,
    val addToCartListener: (value: String) -> Unit,
    val removeFromCartListener: (value: String) -> Unit
) : AdapterDelegate<ProductUiModel>() {

    override fun getViewType() = BasicProductUiModel::class.hashCode()

    override fun isForViewType(items: List<ProductUiModel>, position: Int): Boolean {
        return items[position] is BasicProductUiModel
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemBasicProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_basic_product,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemBasicProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setAddProductListener {
                binding.item?.let { product ->
                    addToCartListener.invoke(product.id)
                }
            }
            binding.setRemoveProductListener {
                binding.item?.let { product ->
                    removeFromCartListener.invoke(product.id)
                }
            }
        }

        fun bind(product: BasicProductUiModel) {
            binding.apply {
                item = product
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(
        item: ProductUiModel,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (holder as ViewHolder).bind(item as BasicProductUiModel)
    }

    override fun onBindViewHolder(
        item: ProductUiModel,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ViewHolder).bind(item as BasicProductUiModel)
    }
}
