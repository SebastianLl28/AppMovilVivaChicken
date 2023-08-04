package com.example.apis

import com.example.response.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIProduct {

  @GET
  suspend fun getProduct(@Url url: String): Response<List<ProductResponse>>
}