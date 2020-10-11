package com.lucasmontano.shopping.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lucasmontano.shopping.ui.models.BasicProductUiModel
import com.lucasmontano.shopping.ui.models.TiledUiModelProduct

class DelegationAdapter<T>(diffCallback: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, RecyclerView.ViewHolder>(diffCallback), BindableAdapter<List<T>> {

    private val adapterDelegatesManager: AdapterDelegatesManager<T> = AdapterDelegatesManager()

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        vararg delegate: AdapterDelegate<T>
    ) : this(diffCallback) {
        delegate.forEach {
            adapterDelegatesManager.addDelegate(it)
        }
    }

    fun addDelegates(vararg delegate: AdapterDelegate<T>) {
        delegate.forEach {
            adapterDelegatesManager.addDelegate(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (this.getItem(position)) {
            is TiledUiModelProduct -> TiledUiModelProduct::class.hashCode()
            is BasicProductUiModel -> BasicProductUiModel::class.hashCode()
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegatesManager.onBindViewHolder(getItem(position), holder, position)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        adapterDelegatesManager.onBindViewHolder(getItem(position), holder, position, payloads)
    }

    override fun setData(data: List<T>?) {
        submitList(data)
    }
}

interface BindableAdapter<T> {
    fun setData(data: T?)
}
