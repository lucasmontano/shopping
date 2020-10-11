package com.lucasmontano.shopping.data.entities

import androidx.room.*
import com.lucasmontano.shopping.utilities.DATABASE_TABLE_CART
import java.util.*

@Entity(
    tableName = DATABASE_TABLE_CART,
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["product_id"]
        )
    ],
    indices = [Index("product_id")]
)
data class ProductShoppingEntity(
    @ColumnInfo(name = "product_id") val productId: String,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "added_cart_date") val addedToCartDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var productShoppingId: Long = 0
}
