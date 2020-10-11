package com.lucasmontano.shopping.data.repositories

import androidx.lifecycle.Transformations
import com.lucasmontano.shopping.data.dao.ProductDao
import com.lucasmontano.shopping.data.domain.*
import com.lucasmontano.shopping.data.entities.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productDao: ProductDao) {

    fun getAllProducts(type: String? = null) = type?.run {
        Transformations.map(productDao.getAllProductsByType(this)) { products ->
            products.mapToDomainModel()
        }
    } ?: Transformations.map(productDao.getAllProducts()) { products ->
        products.mapToDomainModel()
    }

    fun getProduct(productId: String) = productDao.getProduct(productId)

    private fun List<ProductEntity>.mapToDomainModel(): List<HasBasicProductAttr> {
        return this.mapNotNull {
            val productAttr = ProductDomainModel(
                id = it.productId,
                name = it.name,
                imageUrl = it.imageUrl,
                price = 0.0,
                currency = "",
                color = ""
            )
            when (it.type) {
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
    }
}
