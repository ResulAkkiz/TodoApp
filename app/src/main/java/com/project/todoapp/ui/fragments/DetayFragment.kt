package com.project.todoapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.project.todoapp.data.datasource.TodoDataSource
import com.project.todoapp.data.entity.Priority
import com.project.todoapp.data.entity.Todo
import com.project.todoapp.data.entity.toPriorityFromTr
import com.project.todoapp.databinding.FragmentDetayBinding
import com.project.todoapp.room.TodoDatabase
import com.project.todoapp.ui.adapter.PrioritySpinnerAdapter
import com.project.todoapp.ui.viewmodels.AnasayfaFragmentViewModel
import com.project.todoapp.ui.viewmodels.DetayFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

@AndroidEntryPoint
class DetayFragment : Fragment() {
    private var _binding: FragmentDetayBinding? = null
    private val binding get() = _binding!!
    private val args: DetayFragmentArgs by navArgs()
    private lateinit var viewModel: DetayFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetayBinding.inflate(inflater, container, false)
        val view = binding.root
        val todo = args.Todo
        initiliazeContent(todo)

        binding.guncelleButton.setOnClickListener {

            try {
                if (checkFields()) {
                    Snackbar.make(
                        it,
                        "Lütfen gerekli bilgileri doldurunuz ❗",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    updateTodo(todo.id)
                    Snackbar.make(it, "Güncelleme işlemi başarıyla gerçekleşti. ✅", Snackbar.LENGTH_SHORT)
                      .show()
                }

            } catch (e: Exception) {
                Snackbar.make(it, "Hata meydana geldi. ❌", Snackbar.LENGTH_SHORT).show()
            }

        }
        return view
    }

    private fun checkFields(): Boolean =
        binding.detayTitleEditText.text.isNullOrEmpty() || binding.detayDescriptionEditText.text.isNullOrEmpty()

    private fun initiliazeContent(todo: Todo) {
        val priorityLevel = listOf(Priority.Critical, Priority.High, Priority.Normal, Priority.Low)
        val spinnerAdapter = PrioritySpinnerAdapter(priorityLevel, requireContext())

        with(binding) {
            detayTitleEditText.setText(todo.title)
            detayDescriptionEditText.setText(todo.description)
            detayAutoCompletePriorityTextView.setText(todo.priorityLevel.tr())
            detayAutoCompletePriorityTextView.setAdapter(spinnerAdapter)
            detayCheckBox.isChecked = todo.checked
        }
    }

    private fun updateTodo(todoId:Int){
        val updatedTodo = Todo(
            todoId,
            binding.detayTitleEditText.text.toString(),
            binding.detayDescriptionEditText.text.toString(),
            binding.detayCheckBox.isChecked,
            binding.detayAutoCompletePriorityTextView.text.toString().toPriorityFromTr()
        )

        viewModel.updateTodo(updatedTodo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetayFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}