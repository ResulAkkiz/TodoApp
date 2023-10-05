package com.project.todoapp.data.entity

import com.project.todoapp.R

enum class Priority {
    Critical, High, Normal, Low;

    fun tr(): String {
        return when (this) {
            Critical -> "Kritik"
            High -> "Yüksek"
            Normal -> "Normal"
            Low -> "Düşük"
        }
    }

    fun color(): Int {
        return when (this) {
            Critical -> R.color.criticalPriorityColor
            High -> R.color.highPriorityColor
            Normal -> R.color.normalPriorityColor
            Low -> R.color.lowPriorityColor
        }
    }
}

fun String.toPriorityFromTr(): Priority {
    return when (this) {
        "Kritik" -> Priority.Critical
        "Yüksek" -> Priority.High
        "Normal" -> Priority.Normal
        "Düşük" -> Priority.Low
        else -> throw IllegalArgumentException("Geçersiz öncelik: $this")
    }
}
