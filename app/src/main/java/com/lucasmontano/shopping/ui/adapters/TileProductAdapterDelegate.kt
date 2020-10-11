package com.lucasmontano.shopping.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.lucasmontano.shopping.R
import com.lucasmontano.shopping.adapters.AdapterDelegate
import com.lucasmontano.shopping.databinding.ItemTiledProductBinding
import com.lucasmontano.shopping.ui.models.ProductUiModel
import com.lucasmontano.shopping.ui.models.TiledUiModelProduct

internal class TileProductAdapterDelegate(
    private val viewLifecycleOwner: LifecycleOwner,
    val addToCartListener: (value: String?) -> Unit
) : AdapterDelegate<ProductUiModel>() {

    override fun getViewType() = TiledUiModelProduct::class.hashCode()

    override fun isForViewType(items: List<ProductUiModel>, position: Int): Boolean {
        return items[position] is TiledUiModelProduct
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemTiledProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_tiled_product,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemTiledProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TiledUiModelProduct) {
            binding.item = item
        }
    }

    override fun onBindViewHolder(
        item: ProductUiModel,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (holder as ViewHolder).bind(item as TiledUiModelProduct)
    }

    override fun onBindViewHolder(
        item: ProductUiModel,
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as ViewHolder).bind(item as TiledUiModelProduct)
    }
}
