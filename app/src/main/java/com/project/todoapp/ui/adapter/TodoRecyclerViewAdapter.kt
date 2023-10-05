package com.project.todoapp.ui.adapter


import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.project.todoapp.R
import com.project.todoapp.data.entity.Priority
import com.project.todoapp.data.entity.Todo

import com.project.todoapp.databinding.SingleTodoItemBinding
import com.project.todoapp.ui.fragments.AnasayfaFragmentDirections
import com.project.todoapp.ui.viewmodels.AnasayfaFragmentViewModel

class TodoRecyclerViewAdapter(
    var mContext: Context,
    var todoList: List<Todo>,
    var viewModel: AnasayfaFragmentViewModel,
) :
    RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(var view: SingleTodoItemBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun bindView(todo: Todo) {
            view.root.setOnClickListener {
                val direction =
                    AnasayfaFragmentDirections.actionAnasayfaFragmentToDetayFragment(todo)
                Navigation.findNavController(it).navigate(direction)
            }

            view.deleteImageView.setOnClickListener {
                AlertDialog.Builder(mContext).setTitle("Uyarı")
                    .setMessage("Seçmiş olduğunuz görevi silmek istediğinizden emin misiniz ?")
                    .setCancelable(true)
                    .setPositiveButton("Evet") { _, _ ->
                        try {
                            viewModel.deleteTodo(todo)
                            Snackbar.make(it, "Başarıyla Silindi. ✅", Snackbar.LENGTH_SHORT).show()
                        } catch (e: Exception) {
                            Snackbar.make(
                                it,
                                "Silme işlemi sırasında hata meydana geldi. ❌",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .setNegativeButton("Hayır") { dialogInterface, position -> dialogInterface.dismiss() }
                    .show()

            }
            view.todoCheckBox.setOnClickListener {
                val currentState = view.todoCheckBox.isChecked
                val updatedTodo = todo.copy(checked = currentState)

                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    try {
                        viewModel.updateTodo(updatedTodo)
                        if (currentState){
                            Snackbar.make(it, "Görev tamamlananlar listesine eklendi. ✅", Snackbar.LENGTH_SHORT).show()
                        }else{
                            Snackbar.make(it, "Görev tamamlanmayanlar listesine eklendi. ✅", Snackbar.LENGTH_SHORT).show()
                        }

                    }catch (e:Exception){
                        Snackbar.make(
                            it,
                            "Bir hata meydana geldi. ❌",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }


                }, 300)

            }

            view.circleTextView.background = setBackground(todo.priorityLevel)
            view.circleTextView.text = todo.id.toString()
            view.todoCheckBox.isChecked = todo.checked
            view.todoTitle.text = todo.title.trim()
            view.todoDescriptipn.text = todo.description.trim()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(mContext)
        val view = SingleTodoItemBinding.inflate(inflater, parent, false)
        return TodoViewHolder(view);
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentContact = todoList[position]
        holder.bindView(currentContact)
    }

    fun setBackground(priority: Priority): GradientDrawable {
        val shapeDrawable = GradientDrawable()
        shapeDrawable.shape = GradientDrawable.RECTANGLE
        shapeDrawable.cornerRadius = 16f
        val resources = mContext.resources

        when (priority) {
            Priority.Critical -> shapeDrawable.setColor(resources.getColor(R.color.criticalPriorityColor))
            Priority.High -> shapeDrawable.setColor(resources.getColor(R.color.highPriorityColor))
            Priority.Normal -> shapeDrawable.setColor(resources.getColor(R.color.normalPriorityColor))
            Priority.Low -> shapeDrawable.setColor(resources.getColor(R.color.lowPriorityColor))
        }

        return shapeDrawable
    }

}