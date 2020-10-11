package com.lucasmontano.shopping.data.mappers

import com.lucasmontano.shopping.data.domain.*
import com.lucasmontano.shopping.data.entities.ProductEntity

fun ProductEntity.toDomainModel(): HasBasicProductAttr? {
    val productAttr = ProductDomainModel(
        id = productId,
        name = name,
        imageUrl = imageUrl,
        price = 0.0,
        currency = "",
        color = ""
    )
    return when (type) {
        "chair" -> {
            ChairDomainModel(
                productAttr = productAttr,
                materialAttr = ProductWithMaterial("")
            )
        }
        "couch" -> {
            CouchDomainModel(
                productAttr = productAttr,
                seatsAttr = ProductWithSeats(2)
            )
        }
        else -> null
    }
}
