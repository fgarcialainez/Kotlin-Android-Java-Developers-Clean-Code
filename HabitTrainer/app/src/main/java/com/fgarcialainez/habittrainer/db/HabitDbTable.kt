package com.fgarcialainez.habittrainer.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.BaseColumns
import android.util.Log
import com.fgarcialainez.habittrainer.model.Habit
import java.io.ByteArrayOutputStream

class HabitDbTable(context: Context) {

    private val TAG = HabitDbTable::class.java.simpleName

    private val dbHelper = HabitTrainerDb(context)

    fun store(habit: Habit): Long {
        val db = dbHelper.writableDatabase

        val values = ContentValues()
        with (values) {
            put(HabitEntry.TITLE_COL, habit.title)
            put(HabitEntry.DESCRIPTION_COL, habit.description)
            put(HabitEntry.IMAGE_COL, toByteArray(habit.image))
        }

        val id = db.transaction {
            insert(HabitEntry.TABLE_NAME, null, values)
        }

        Log.d(TAG, "Stored new habit in the DB: $habit")

        return id
    }

    fun readAllHabits(): List<Habit> {
        val columns = arrayOf(BaseColumns._ID, HabitEntry.TITLE_COL, HabitEntry.DESCRIPTION_COL, HabitEntry.IMAGE_COL)
        val order = "${BaseColumns._ID} ASC"

        val db = dbHelper.readableDatabase
        val cursor = db.query(HabitEntry.TABLE_NAME, columns, order)

        return parseHabitsFrom(cursor)
    }

    private fun parseHabitsFrom(cursor: Cursor): List<Habit> {
        val habits = mutableListOf<Habit>()

        while (cursor.moveToNext()) {
            val title = cursor.getString(HabitEntry.TITLE_COL)
            val description = cursor.getString(HabitEntry.DESCRIPTION_COL)
            val bitmap = cursor.getBitmap(HabitEntry.IMAGE_COL)

            habits.add(Habit(title, description, bitmap))
        }

        cursor.close()

        return habits
    }

    private fun toByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)

        return stream.toByteArray()
    }
}

private inline fun <T> SQLiteDatabase.transaction(function: SQLiteDatabase.() -> T): T {
    // Begin the transaction
    beginTransaction()

    val result = try {
        // Call generic function
        val returnValue = function()

        // Mark the transaction as completed successfully
        setTransactionSuccessful()

        // Return the generic value
        returnValue
    } finally {
        // End the transaction
        endTransaction()
    }

    // Close the database
    close()

    return result
}

private fun SQLiteDatabase.query(table: String, columns: Array<String>, orderBy: String): Cursor {
    return query(table, columns, null, null, null, null, orderBy)
}

private fun Cursor.getString(columnName: String) = getString(getColumnIndex(columnName))

private fun Cursor.getBitmap(columnName: String): Bitmap {
    val byteArray = getBlob(getColumnIndex(columnName))
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}