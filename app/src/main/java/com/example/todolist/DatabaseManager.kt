package com.example.todolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

const val DATA_BASE_NAME = "My SQL2"
const val TABLE_NAME = "todos"
const val COL_TITLE = "title"
const val COL_ISDONE = "isDone"
const val COL_ID = "id"

class DataBaseManager(private val context: Context) : SQLiteOpenHelper(context, DATA_BASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_TITLE VARCHAR(256),$COL_ISDONE TINYINT(1))"
//        val createTable = "CREATE TABLE " + TABLE_NAME + "(" +
//                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//        COL_TITLE + " VARCHAR(255)," +
//                COL_ISDONE + " TINYINT(1)" + ")"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertData(todo: Todo) {
        val db = writableDatabase
        val cv = ContentValues()
        //cv.put(COL_ID, todo.ID)
        cv.put(COL_TITLE, todo.title)
        cv.put(COL_ISDONE, todo.isDone)
        val result = db.insert(TABLE_NAME, null, cv)
        if (result == (0).toLong()) { //0 to -1
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData(): MutableList<Todo> {
        val list: MutableList<Todo> = ArrayList()

        val db = readableDatabase
        val query = "Select * from $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                val title = result.getString(result.getColumnIndex(COL_TITLE))
                val isDone = result.getInt(result.getColumnIndex(COL_ISDONE)) == 1
                val todo = Todo(id, title, isDone)
                list.add(todo)

            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun updateDataDB(todo: Todo) {
        val db = writableDatabase

        val cv = ContentValues()
        cv.put(COL_ISDONE, todo.isDone)
        db.update(TABLE_NAME, cv, "$COL_ID=?", arrayOf(todo.ID.toString()))

        db.close()

    }

    fun deleteDate() {
        val db = writableDatabase
        val whereClause = "isDone = 1"
        val whereArgs = arrayOf<String>(1.toString())
        db.delete(TABLE_NAME, whereClause, null)
        db.close()
    }

}