package com.example.androidacademyapi.ui.productdetails

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.androidacademyapi.data.repository.ProductRepository
import com.example.androidacademyapi.ui.productlistscreen.ProductListUIState
import com.example.androidacademyapi.ui.productlistscreen.ProductListViewModel
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: ProductRepository,
    private val productId: Int
) : ViewModel() {
    private val _uiState: MutableState<ProductDetailsUIState> = mutableStateOf(ProductDetailsUIState.Loading)
    val uiState: State<ProductDetailsUIState> = _uiState

    init {
        fetchProduct()
    }

    fun retryFetch() {
        fetchProduct()
    }
    private fun fetchProduct() {
        viewModelScope.launch {
            _uiState.value = ProductDetailsUIState.Loading
            if (productId < 0) {
                _uiState.value = ProductDetailsUIState.Error("Neispravan id: $productId")
                return@launch
            }

            repository.getProductsById(productId)
                .onSuccess { product ->
                    _uiState.value = ProductDetailsUIState.Success(product)
                }
                .onFailure { error ->
                    if (error is java.net.UnknownHostException || error is java.io.IOException) {
                        _uiState.value = ProductDetailsUIState.NoInternet
                    } else {
                        _uiState.value = ProductDetailsUIState.Error(error.message ?: "Unknown Error")
                    }
                }
        }
    }
}

class ProductDetailsViewModelFactory(
    private val repository: ProductRepository,
    private val productId: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(repository,productId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}