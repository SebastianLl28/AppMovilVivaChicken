package com.example.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.R
import com.example.adapters.CategoryAdapter
import com.example.apis.APICategory
import com.example.databinding.FragmentHomeBinding
import com.example.response.CategoryResponse
import com.example.response.GlobalVariable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

  private var _binding: FragmentHomeBinding? = null

  private lateinit var adapter: CategoryAdapter
  private val categoryList = mutableListOf<CategoryResponse>()
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
    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    progressBar = binding.progressBar
    initRecyclerView()
    loadDataFromApi()
  }

  private fun loadDataFromApi() {
    val job = CoroutineScope(Dispatchers.IO).async {
      try {
        activity?.runOnUiThread { progressBar.visibility = View.VISIBLE }
        val res = getCategoryList().create(APICategory::class.java).getCategory("categorias")
        val product = res.body()
        activity?.runOnUiThread {
          if (product != null) {
            categoryList.addAll(product)
            adapter.notifyDataSetChanged()

            // Verificar si la lista no está vacía antes de notificar al adaptador
            if (categoryList.isNotEmpty()) {
              adapter.notifyDataSetChanged()
            }
          }

          // Ocultar el ProgressBar en caso de error o éxito
          progressBar.visibility = View.GONE
        }
      } catch (e: Exception) {
        println(e.message)
        e.printStackTrace()
        // Ocultar el ProgressBar en caso de error
        progressBar.visibility = View.GONE
      }
    }
  }


  private fun initRecyclerView() {
    adapter = CategoryAdapter(categoryList)
//    binding.rvCategoria.layoutManager = LinearLayoutManager(requireContext())
    binding.rvCategoria.layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)
    binding.rvCategoria.adapter = adapter
  }

  private fun getCategoryList():Retrofit {
    return Retrofit.Builder().baseUrl(GlobalVariable.baseURL)
      .addConverterFactory(GsonConverterFactory.create()).build()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  companion object {

    private const val ARG_PARAM1 = "param1"
    private const val ARG_PARAM2 = "param2"

    @JvmStatic
    fun newInstance(param1: String, param2: String) =
      HomeFragment()
        .apply {
        arguments = Bundle().apply {
          putString(ARG_PARAM1, param1)
          putString(ARG_PARAM2, param2)
        }
      }
  }
}