package com.lucasmontano.shopping.data.repositories

import com.lucasmontano.shopping.data.dao.ProductDao

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts() = productDao.getAllProducts()

    fun getProduct(productId: String) = productDao.getProduct(productId)
}
