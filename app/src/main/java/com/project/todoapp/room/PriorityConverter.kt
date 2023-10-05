package com.project.todoapp.room

import androidx.room.TypeConverter
import com.project.todoapp.data.entity.Priority

class PriorityConverter {
    @TypeConverter
    fun fromPriority(priority: Priority): Int {
        return priority.ordinal
    }

    @TypeConverter
    fun toPriority(value: Int): Priority {
        return Priority.values()[value]
    }
}