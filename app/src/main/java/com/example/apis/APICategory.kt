package com.example.apis

import com.example.response.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APICategory {

  @GET
  suspend fun getCategory(@Url url: String): Response<List<CategoryResponse>>
}