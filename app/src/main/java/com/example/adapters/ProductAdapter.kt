package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.holder.ProductViewHolder
import com.example.response.ProductResponse

class ProductAdapter(private val listProductResponse: List<ProductResponse>): RecyclerView.Adapter<ProductViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return ProductViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
  }

  override fun getItemCount(): Int =  listProductResponse.size

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    val item = listProductResponse[position]
    holder.bind(item)
  }
}