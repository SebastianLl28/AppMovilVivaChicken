package com.example.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.R
import com.example.holder.CategoryViewHolder
import com.example.response.CategoryResponse

class CategoryAdapter(private val listCategoryResponse: List<CategoryResponse>) : RecyclerView.Adapter<CategoryViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    return CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
  }
  override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
    val item = listCategoryResponse[position]
    holder.bind(item)
  }
  override fun getItemCount(): Int = listCategoryResponse.size
}