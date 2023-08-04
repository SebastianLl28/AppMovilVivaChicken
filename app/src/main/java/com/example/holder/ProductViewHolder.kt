package com.example.holder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.databinding.ItemCategoryBinding
import com.example.databinding.ItemProductBinding
import com.example.response.ProductResponse
import com.squareup.picasso.Picasso

class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {

  private val binding = ItemProductBinding.bind(view)

  fun bind(product: ProductResponse) {
    binding.tvProductName.text = product.nombre
    binding.tvDescriptionProduct.text = product.descripcion
    binding.tvPriceProduct.text = "S/.${product.precio}"
    Picasso.get().load(product.imagen).into(binding.ivProduct)

    binding.btnAgregarProducto.setOnClickListener {
      Toast.makeText( itemView.context, "${product.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
    }
  }
}