package com.example.todolist

import kotlin.random.Random

data class Todo(
    var ID: Int =0,
    var title: String = "",
    var isDone: Boolean = false
) {
    constructor(title: String) : this() {
        this.title = title
    }

    constructor(title: String, isDone: Boolean) : this() {
        this.title = title
        this.isDone = isDone
    }
}
