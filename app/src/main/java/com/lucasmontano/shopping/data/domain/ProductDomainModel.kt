package com.lucasmontano.shopping.data.domain

data class ProductDomainModel(
    override val id: String,
    override val name: String,
    override val price: Double,
    override val currency: String,
    override val color: String,
    override val imageUrl: String
) : HasBasicProductAttr

interface HasBasicProductAttr {
    val id: String
    val name: String
    val price: Double
    val currency: String
    val color: String
    val imageUrl: String
}

interface HasSeats {
    val numberOfSeats: Int
}

interface HasMaterial {
    val material: String
}

data class ProductWithSeats(override val numberOfSeats: Int) : HasSeats

data class ProductWithMaterial(override val material: String) : HasMaterial
