package com.project.todoapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.project.todoapp.R
import com.project.todoapp.data.entity.Priority
import com.project.todoapp.data.entity.Todo
import com.project.todoapp.data.entity.toPriorityFromTr
import com.project.todoapp.databinding.FragmentKayitBinding
import com.project.todoapp.ui.adapter.PrioritySpinnerAdapter
import com.project.todoapp.ui.viewmodels.KayitFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class KayitFragment : Fragment() {
    private var _binding: FragmentKayitBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: KayitFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentKayitBinding.inflate(inflater, container, false)
        val view = binding.root
        val priorityLevel = listOf(Priority.Critical, Priority.High, Priority.Normal, Priority.Low)
        val spinnerAdapter = PrioritySpinnerAdapter(priorityLevel, requireContext())
        binding.kayitAutoCompletePriorityTextView.setAdapter(spinnerAdapter)


        binding.kayitButton.setOnClickListener {
            try {
                if (checkFields()) {
                    Snackbar.make(
                        it,
                        "Lütfen gerekli bilgileri doldurunuz ❗",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    insertTodo()
                    Snackbar.make(it, "Başarıyla listeye eklendi. ✅", Snackbar.LENGTH_SHORT)
                        .setAction("Listeyi Göster") { it ->
                            Navigation.findNavController(it).popBackStack()
                            //Todo:Burayı başka bir bilgisayar ile kontrol et
                        }.show()
                }

            } catch (e: Exception) {
                Snackbar.make(it, "Hata meydana geldi. ❌", Snackbar.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun checkFields(): Boolean =
        binding.kayitAutoCompletePriorityTextView.text.isNullOrEmpty() || binding.kayitDescriptionEditText.text.isNullOrEmpty() || binding.kayitTitleEditText.text.isNullOrEmpty()

    private fun insertTodo() {
        val insertedTodo = Todo(
            0,
            binding.kayitTitleEditText.text.toString(),
            binding.kayitDescriptionEditText.text.toString(),
            false,
            binding.kayitAutoCompletePriorityTextView.text.toString().toPriorityFromTr()
        )
        viewModel.insertTodo(insertedTodo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: KayitFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}