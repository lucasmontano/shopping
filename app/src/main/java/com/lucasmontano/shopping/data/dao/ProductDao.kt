package com.lucasmontano.shopping.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_PRODUCTS

@Dao
interface ProductDao {
    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS ORDER BY name")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE type = :type ORDER BY name")
    fun getAllProductsByType(type: String): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE isOnCart = 1 ORDER BY name")
    fun getOnCartProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE id = :productId")
    fun getProduct(productId: String): LiveData<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productList: List<ProductEntity>)

    @Update
    suspend fun update(productEntity: ProductEntity)
}
