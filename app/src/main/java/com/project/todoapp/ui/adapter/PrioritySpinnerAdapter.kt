package com.project.todoapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.project.todoapp.data.entity.Priority
import com.project.todoapp.databinding.PrioritySpinnerItemBinding

class PrioritySpinnerAdapter(
    private var priorityList: List<Priority>,
    private var mContext: Context,
) :
    BaseAdapter(), Filterable {
    override fun getCount(): Int {
        return priorityList.size
    }

    override fun getItem(position: Int): Any {
        return priorityList[position].tr()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val currentPriority = priorityList[position]

        val binding =
            PrioritySpinnerItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        binding.colorCircle.background = setBackground(currentPriority)
        binding.priorityTextView.text = currentPriority.tr()
        return binding.root
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredItems = mutableListOf<Priority>()


                val filterResults = FilterResults()
                filterResults.values = filteredItems
                filterResults.count = filteredItems.size

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }


    private fun setBackground(priority: Priority): GradientDrawable {
        val shapeDrawable = GradientDrawable()
        shapeDrawable.shape = GradientDrawable.OVAL
        shapeDrawable.setColor(mContext.resources.getColor(priority.color()))
        return shapeDrawable
    }
}
