package com.example.todolist

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todos: MutableList<Todo>, private val context: Context) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private val db: DataBaseManager = DataBaseManager(context)

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvTodoTitle)
        val checkbox: CheckBox = itemView.findViewById(R.id.cbDone);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isDone
        }

        notifyDataSetChanged()
    }



    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.title.text = curTodo.title
        holder.checkbox.isChecked = curTodo.isDone
        toggleStrikeThrough(holder.title, holder.checkbox.isChecked)
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            toggleStrikeThrough(holder.title, isChecked)
            curTodo.isDone = !curTodo.isDone
            db.updateDataDB(curTodo)
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}