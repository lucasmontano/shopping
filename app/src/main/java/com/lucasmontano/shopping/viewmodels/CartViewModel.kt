package com.lucasmontano.shopping.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.lucasmontano.shopping.data.entities.ProductCartRelation
import com.lucasmontano.shopping.data.repositories.CartRepository
import kotlinx.coroutines.launch

class CartViewModel @ViewModelInject internal constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    private val cart: LiveData<List<ProductCartRelation>> = cartRepository.getAllProducts()

    val uiState: LiveData<ViewState> = Transformations.switchMap(cart) {
        // TODO map to domain model
        var total = 0.0
        val currency = cart.value?.firstOrNull()?.product?.price?.currency ?: "EUR"
        cart.value?.forEach {
            total += it.product.price.value
        }
        MutableLiveData(ViewState("$currency $$total"))
    }

    fun addToCart(productId: String) = viewModelScope.launch {
        cartRepository.addProduct(productId)
    }

    data class ViewState(val total: String)
}