package com.fgarcialainez.habittrainer.db

import android.provider.BaseColumns

val DATABASE_VERSION = 10
val DATABASE_NAME = "habittrainer.db"

object HabitEntry: BaseColumns {
    val TABLE_NAME = "habit"
    val TITLE_COL = "title"
    val DESCRIPTION_COL = "description"
    val IMAGE_COL = "image"
}