package com.lucasmontano.shopping.data.entities

import androidx.room.Embedded
import androidx.room.Relation

// TODO check if we need to have redudance of products in the cart
data class ProductCartRelation(
    @Embedded
    val product: ProductEntity,

    @Relation(parentColumn = "id", entityColumn = "product_id")
    val productShoppingEntities: List<ProductShoppingEntity> = emptyList()
)
