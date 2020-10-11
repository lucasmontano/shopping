package com.lucasmontano.shopping.data.repositories

import androidx.lifecycle.Transformations
import com.lucasmontano.shopping.data.dao.ProductDao
import com.lucasmontano.shopping.data.domain.HasBasicProductAttr
import com.lucasmontano.shopping.data.entities.ProductEntity
import com.lucasmontano.shopping.data.mappers.toDomainModel
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
            it.toDomainModel()
        }
    }
}
