package com.example.response

data class ProductResponse(
  val id: Int,
  val imagen: String,
  val descripcion: String,
  val nombre: String,
  val precio: Double,
  val stock: Int,
  val categoria: CategoryResponse
)