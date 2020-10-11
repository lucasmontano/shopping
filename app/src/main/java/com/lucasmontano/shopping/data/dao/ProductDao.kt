package com.lucasmontano.shopping.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_PRODUCTS

@Dao
interface ProductDao {
    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS ORDER BY name")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE type = :type ORDER BY name")
    fun getAllProductsByType(type: String): LiveData<List<ProductEntity>>

    @Query("SELECT * FROM $DATABASE_TABLE_PRODUCTS WHERE id = :productId")
    fun getProduct(productId: String): LiveData<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productList: List<ProductEntity>)
}
