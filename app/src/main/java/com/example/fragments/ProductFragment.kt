package com.example.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.adapters.CategoryAdapter
import com.example.adapters.ProductAdapter
import com.example.apis.APIProduct
import com.example.databinding.FragmentHomeBinding
import com.example.databinding.FragmentProductBinding
import com.example.response.CategoryResponse
import com.example.response.GlobalVariable
import com.example.response.ProductResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ProductFragment : Fragment() {

  private var _binding: FragmentProductBinding? = null

  private lateinit var adapter: ProductAdapter
  private val productList = mutableListOf<ProductResponse>()
  private lateinit var progressBar: ProgressBar
  private val binding get() = _binding!!


  private var param1: String? = null
  private var param2: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      param1 = it.getString(ARG_PARAM1)
      param2 = it.getString(ARG_PARAM2)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_product, container, false)
    _binding = FragmentProductBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    progressBar = binding.progressBar
    initRecyclerView()
    loadDataFromApi()
  }

  private fun initRecyclerView() {
    adapter = ProductAdapter(productList)
    binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
    binding.rvProduct.adapter = adapter
  }

  private fun loadDataFromApi() {
    val job = CoroutineScope(Dispatchers.IO).async {
      try {
        activity?.runOnUiThread { progressBar.visibility = View.VISIBLE }
        val res = getProductList().create(APIProduct::class.java).getProduct("productos")
        val product = res.body()
        activity?.runOnUiThread {
          if (product != null ){
            productList.addAll(product)
            adapter.notifyDataSetChanged()

            if(productList.isNotEmpty()){
              adapter.notifyDataSetChanged()
            }
          }
          progressBar.visibility = View.GONE
        }
      } catch (e: Exception) {
        println("ERROOOOOOOOOOOOOOOOOOOOORRRRRRRRR")
        println(e.message)
        e.printStackTrace()
      }
    }
  }

  private fun getProductList() : Retrofit{
    return Retrofit.Builder().baseUrl(GlobalVariable.baseURL).addConverterFactory(GsonConverterFactory.create()).build()
  }

  companion object {
    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      ProductFragment().apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}