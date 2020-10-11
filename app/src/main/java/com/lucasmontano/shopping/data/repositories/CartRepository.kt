package com.lucasmontano.shopping.data.repositories

import com.lucasmontano.shopping.data.dao.ProductDao
import com.lucasmontano.shopping.data.entities.ProductEntity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts() = productDao.getOnCartProducts()

    suspend fun addProduct(productEntity: ProductEntity) = updateProduct(productEntity, true)

    suspend fun removeProduct(productEntity: ProductEntity) = updateProduct(productEntity, false)

    private suspend fun updateProduct(productEntity: ProductEntity, isOnCart: Boolean) =
        productDao.update(productEntity.copy(isOnCart = isOnCart))

}
