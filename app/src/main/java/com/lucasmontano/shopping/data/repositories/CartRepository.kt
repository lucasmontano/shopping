package com.lucasmontano.shopping.data.repositories

import com.lucasmontano.shopping.data.dao.CartDao
import com.lucasmontano.shopping.data.entities.ProductShoppingEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    suspend fun addProduct(productId: String, quantity: Int = 1) {
        cartDao.addProduct(ProductShoppingEntity(productId, quantity))
    }

    suspend fun removeProduct(productShoppingEntity: ProductShoppingEntity) {
        cartDao.removeProduct(productShoppingEntity)
    }

    fun isOnCart(productId: String) =
        cartDao.isAddedToCart(productId)

    fun getAllProducts() = cartDao.getAllProducts()
}
