package com.example.androidacademyapi.data.repository

import com.example.androidacademyapi.data.network.apiservice.KtorProductApiService
import com.example.androidacademyapi.data.model.Product
import com.example.androidacademyapi.data.model.ProductRequest

class KtorProductRepository(private val ktorProductApiService: KtorProductApiService): ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> {
        return runCatching {
            ktorProductApiService.getProducts().products
        }
    }

    override suspend fun addProduct(request: ProductRequest): Result<Product> {
        return runCatching {
            ktorProductApiService.addProduct(request)
        }
    }

    override suspend fun updateProduct(
        id: Int,
        request: ProductRequest
    ): Result<Product> {
        return runCatching {
            ktorProductApiService.updateProduct(id,request)
        }
    }

    override suspend fun deleteProduct(id: Int): Result<Product> {
        return runCatching {
            ktorProductApiService.deleteProduct(id)
        }
    }

    override suspend fun getProductsById(id: Int): Result<Product> {
        TODO("Not yet implemented")
    }
}