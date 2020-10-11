package com.lucasmontano.shopping.data.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.repositories.ProductRepository

class ProductListViewModel @ViewModelInject internal constructor(
    productRepository: ProductRepository
) : ViewModel() {

    private val filter = MutableLiveData<FilterState>()

    val products: LiveData<List<ProductEntity>> = Transformations.switchMap(filter) {
        when (filter.value) {
            is FilterState.Type -> {
                it as FilterState.Type
                productRepository.getAllProducts(it.type)
            }
            else -> productRepository.getAllProducts()
        }
    }

    init {
        filter.postValue(FilterState.Empty)
    }

    fun clearFilter() = filter.postValue(FilterState.Empty)

    fun filterByType(type: String) = filter.postValue(FilterState.Type(type))

    private sealed class FilterState {
        object Empty : FilterState()
        data class Type(val type: String) : FilterState()
    }
}
