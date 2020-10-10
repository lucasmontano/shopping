package com.lucasmontano.shopping.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_PRODUCTS

@Entity(tableName = DATABASE_TABLE_PRODUCTS)
data class ProductEntity(
    @PrimaryKey @ColumnInfo(name = "id") val productId: String,
    val name: String,
    val description: String,
    val imageUrl: String = ""
) {

    override fun toString() = name
}
