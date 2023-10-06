package com.project.todoapp.ui.fragments

import android.R.string
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.todoapp.R
import com.project.todoapp.databinding.FragmentAnasayfaBinding
import com.project.todoapp.ui.adapter.TodoRecyclerViewAdapter
import com.project.todoapp.ui.viewmodels.AnasayfaFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private var _binding: FragmentAnasayfaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnasayfaFragmentViewModel
    var isEn=true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        println("onCreateView yeniden tetiklendi")
        _binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        val view = binding.root

//        changeLanguage("tr")
        viewModel.todoList.observe(viewLifecycleOwner) { todoList ->
            val checkedList = todoList.filter { todo -> todo.checked }.sortedBy { it.priorityLevel }
            val uncheckedList =
                todoList.filter { todo -> !todo.checked }.sortedBy { it.priorityLevel }
            val adapterUnchecked =
                TodoRecyclerViewAdapter(requireContext(), uncheckedList, viewModel,isEn)
            binding.uncheckedTodoList.adapter = adapterUnchecked
            val adapterChecked =
                TodoRecyclerViewAdapter(requireContext(), checkedList, viewModel,isEn)
            binding.checkedTodoList.adapter = adapterChecked
        }

        binding.languageImageView.setOnClickListener {
           isEn = !isEn
            println(isEn)
            val languageCode = if (isEn) "en" else "tr"
            changeLanguage(languageCode)
        }

        binding.uncheckedTodoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.checkedTodoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.floatingActionButton.setOnClickListener {
            val direction =
                AnasayfaFragmentDirections.actionAnasayfaFragmentToKayitFragment(isEn)
            Navigation.findNavController(it).navigate(direction)
        }

        binding.todoSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {  // arama ikonuna basıldığında çalışır
                ara(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {  // harf girdikçe veya sildikçe çalışır
                ara(query)
                return true
            }
        })

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("boolean_value", isEn)
        super.onSaveInstanceState(outState)
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        isEn = savedInstanceState?.getBoolean("boolean_value") ?: true
        super.onViewStateRestored(savedInstanceState)
    }

    private fun changeLanguage(languageCode: String) {
        println(languageCode)
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
        recreate(requireActivity())
    }

    fun ara(query: String) {
        viewModel.searchTodo(query)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}