package com.lucasmontano.shopping.ui.models

interface ProductUiModel {
    val id: String
}

data class TiledUiModelProduct(
    override val id: String,
    val name: String,
    val imageUrl: String
) : ProductUiModel

data class BasicProductUiModel(override val id: String, val name: String) : ProductUiModel