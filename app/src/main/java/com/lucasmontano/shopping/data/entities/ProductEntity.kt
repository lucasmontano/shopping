package com.lucasmontano.shopping.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_PRODUCTS

@Entity(tableName = DATABASE_TABLE_PRODUCTS)
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val productId: String,
    val name: String,
    val type: String,
    val imageUrl: String = "",
    // TODO create a cart entity so we can resolve conflicts (prices, availability...)
    val isOnCart: Boolean = false
) {

    override fun toString() = name
}
