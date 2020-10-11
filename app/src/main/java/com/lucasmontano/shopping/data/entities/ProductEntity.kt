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
    val price: Price
) {

    override fun toString() = name

    data class Price(val value: Double, val currency: String)
}
