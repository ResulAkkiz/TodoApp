package com.project.todoapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "todo")
data class Todo(
    @ColumnInfo(name = "todo_id",) @PrimaryKey(autoGenerate = true) @NotNull var id: Int,
    @ColumnInfo(name = "todo_title",) @NotNull var title: String,
    @ColumnInfo(name = "todo_description") @NotNull var description: String,
    @ColumnInfo(name = "todo_checked") @NotNull var checked: Boolean = false,
    @ColumnInfo(name = "todo_priority") @NotNull var priorityLevel: Priority,
) : Serializable