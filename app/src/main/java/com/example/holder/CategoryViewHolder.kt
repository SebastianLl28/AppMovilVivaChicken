package com.example.holder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemCategoryBinding
import com.example.response.CategoryResponse
import com.squareup.picasso.Picasso

class CategoryViewHolder (view: View):  RecyclerView.ViewHolder(view){

  private val binding = ItemCategoryBinding.bind(view)
  fun bind(category: CategoryResponse){
    Picasso.get().load(category.imagen).into(binding.ivCategory)
    binding.tvCategoryName.text = category.nombre

    binding.ivCategory.setOnClickListener{
      Toast.makeText(itemView.context,  "Click en ${category.nombre}", Toast.LENGTH_SHORT).show()
    }
  }
}