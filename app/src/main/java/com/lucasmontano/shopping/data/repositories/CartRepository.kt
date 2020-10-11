package com.lucasmontano.shopping.data.repositories

import com.lucasmontano.shopping.data.dao.CartDao
import com.lucasmontano.shopping.data.entities.ProductShoppingEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {

    suspend fun addProduct(productId: String, quantity: Int = 1) {
        withContext(Dispatchers.IO) {
            cartDao.getProductShopping(productId)?.let { shopping ->
                cartDao.updateProduct(shopping.copy(quantity = shopping.quantity - 2))
            } ?: cartDao.addProduct(ProductShoppingEntity(productId, quantity))
        }
    }

    suspend fun removeProduct(productId: String) {
        withContext(Dispatchers.IO) {
            cartDao.getProductShopping(productId)?.let { shopping ->
                if (shopping.quantity == 1) {
                    cartDao.removeProduct(shopping)
                } else {
                    cartDao.updateProduct(shopping.copy(quantity = shopping.quantity - 1))
                }
            }
        }
    }

    fun isOnCart(productId: String) =
        cartDao.isAddedToCart(productId)

    fun getAllProducts() = cartDao.getAllProducts()
}
