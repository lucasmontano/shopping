package com.lucasmontano.shopping.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.lucasmontano.shopping.data.domain.HasBasicProductAttr
import com.lucasmontano.shopping.data.repositories.CartRepository
import com.lucasmontano.shopping.data.repositories.ProductRepository
import com.lucasmontano.shopping.ui.models.BasicProductUiModel
import com.lucasmontano.shopping.ui.models.ProductUiModel
import com.lucasmontano.shopping.ui.models.TiledUiModelProduct

class ProductListViewModel @ViewModelInject internal constructor(
    productRepository: ProductRepository
) : ViewModel() {

    private val filter = MutableLiveData<FilterState>()

    private val products: LiveData<List<ProductUiModel>> = Transformations.switchMap(filter) {
        when (filter.value) {
            is FilterState.Type -> {
                it as FilterState.Type
                Transformations.map(productRepository.getAllProducts(it.type)) { products ->
                    products.toUiModel()
                }
            }
            else -> Transformations.map(productRepository.getAllProducts()) { products ->
                products.toUiModel()
            }
        }
    }

    val uiState: LiveData<ViewState> = Transformations.switchMap(products) {
        MutableLiveData(ViewState(it))
    }

    init {
        filter.postValue(FilterState.Empty)
    }

    fun clearFilter() = filter.postValue(FilterState.Empty)

    fun filterByType(type: String) = filter.postValue(FilterState.Type(type))

    private fun List<HasBasicProductAttr>.toUiModel(): List<ProductUiModel> {
        return this.mapIndexed { index, productEntity ->
            when (index) {
                0 -> TiledUiModelProduct(
                    productEntity.id,
                    productEntity.name,
                    productEntity.imageUrl
                )
                else -> BasicProductUiModel(productEntity.id, productEntity.name)
            }
        }
    }

    data class ViewState(val products: List<ProductUiModel>)

    private sealed class FilterState {
        object Empty : FilterState()
        data class Type(val type: String) : FilterState()
    }
}
