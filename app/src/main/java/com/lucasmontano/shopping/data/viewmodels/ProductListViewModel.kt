package com.lucasmontano.shopping.data.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.repositories.ProductRepository

class ProductListViewModel @ViewModelInject internal constructor(
    productRepository: ProductRepository
) : ViewModel() {

    val products: LiveData<List<ProductEntity>> = productRepository.getAllProducts()

}
