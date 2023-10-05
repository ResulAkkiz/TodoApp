package com.project.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.todoapp.R
import com.project.todoapp.databinding.FragmentAnasayfaBinding
import com.project.todoapp.ui.adapter.TodoRecyclerViewAdapter
import com.project.todoapp.ui.viewmodels.AnasayfaFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private var _binding: FragmentAnasayfaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AnasayfaFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.todoList.observe(viewLifecycleOwner){
            todoList->
            val checkedList=todoList.filter { todo->todo.checked }.sortedBy { it.priorityLevel }
            val uncheckedList=todoList.filter { todo->!todo.checked }.sortedBy { it.priorityLevel }
            val adapterUnchecked =
                TodoRecyclerViewAdapter(requireContext(), uncheckedList,viewModel)
            binding.uncheckedTodoList.adapter = adapterUnchecked
            val adapterChecked =
                TodoRecyclerViewAdapter(requireContext(), checkedList,viewModel)
            binding.checkedTodoList.adapter = adapterChecked
        }

        binding.uncheckedTodoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.checkedTodoList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.floatingActionButton.setOnClickListener {

            Navigation.findNavController(it).navigate(R.id.action_anasayfaFragment_to_kayitFragment)
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