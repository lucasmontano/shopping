package com.lucasmontano.shopping.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lucasmontano.shopping.data.entities.ProductCartRelation
import com.lucasmontano.shopping.data.entities.ProductShoppingEntity
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_CART
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_PRODUCTS

@Dao
interface CartDao {

    @Query("SELECT * FROM $DATABASE_TABLE_CART")
    fun getProductsShopping(): LiveData<List<ProductShoppingEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_CART WHERE product_id = :productId LIMIT 1")
    fun getProductShopping(productId: String): ProductShoppingEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM $DATABASE_TABLE_CART WHERE product_id = :productId LIMIT 1)")
    fun isAddedToCart(productId: String): LiveData<Boolean>

    @Transaction
    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE id IN (SELECT DISTINCT(product_id) FROM $DATABASE_TABLE_CART)")
    fun getAllProducts(): LiveData<List<ProductCartRelation>>

    @Insert
    suspend fun addProduct(productShoppingEntity: ProductShoppingEntity): Long

    @Delete
    suspend fun removeProduct(productShoppingEntity: ProductShoppingEntity)

    @Update
    suspend fun updateProduct(productShoppingEntity: ProductShoppingEntity)
}
