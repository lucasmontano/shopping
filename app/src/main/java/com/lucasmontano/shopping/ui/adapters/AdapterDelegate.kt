package com.lucasmontano.shopping.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AdapterDelegate<in T> {

    abstract fun getViewType(): Int

    abstract fun isForViewType(items: List<T>, position: Int): Boolean

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindViewHolder(
        item: T,
        holder: RecyclerView.ViewHolder,
        position: Int
    )

    abstract fun onBindViewHolder(
        item: T,
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    )
}
