package com.lucasmontano.shopping.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterDelegatesManager<T> {

    private val delegates: HashMap<Int, AdapterDelegate<T>> = HashMap()

    fun addDelegate(delegate: AdapterDelegate<T>) {
        delegates[delegate.getViewType()] = delegate
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateForViewType(viewType)?.onCreateViewHolder(parent, viewType)
            ?: throw Exception("No Delegate Found for ViewType $viewType")
    }

    fun onBindViewHolder(item: T, holder: RecyclerView.ViewHolder, position: Int) {
        delegateForViewType(holder.itemViewType)?.onBindViewHolder(item, holder, position)
            ?: throw Exception("No Delegate Found for ViewType ${holder.itemViewType}")
    }

    fun onBindViewHolder(
        item: T,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payload: MutableList<Any>
    ) {
        delegateForViewType(holder.itemViewType)?.onBindViewHolder(item, holder, position, payload)
            ?: throw Exception("No Delegate Found for ViewType ${holder.itemViewType}")
    }

    private fun delegateForViewType(viewType: Int): AdapterDelegate<T>? = delegates.get(viewType)

}
