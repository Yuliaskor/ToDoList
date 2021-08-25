package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter
    private lateinit var rvTodoItems: RecyclerView
    private lateinit var btnAddTodo: Button
    private lateinit var btnDeleteDoneTodo: Button
    private lateinit var etTodoTitle: EditText
    private lateinit var cbDone : CheckBox
    private val db: DataBaseManager = DataBaseManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoAdapter = TodoAdapter(mutableListOf(), this)

        rvTodoItems = findViewById(R.id.rvTodoItems)
        btnAddTodo = findViewById(R.id.btnAddTodo)
        etTodoTitle = findViewById(R.id.etTodoTitle)
        btnDeleteDoneTodo = findViewById(R.id.btnDeleteTodo)
        println(findViewById(R.id.SHOW_ALL))

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)


        
        val data = db.readData()


        for (i in 0 until data.size) {
            println(data[i])
            todoAdapter.addTodo(data[i])
        }

        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)

                todoAdapter.addTodo(todo)

                val tododb = Todo(etTodoTitle.text.toString())
                db.insertData(tododb)



                etTodoTitle.text.clear()

            }
        }


        btnDeleteDoneTodo.setOnClickListener {
            db.deleteDate()

            todoAdapter.deleteDoneTodos()

        }

    }

}