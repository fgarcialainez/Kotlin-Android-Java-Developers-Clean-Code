package com.fgarcialainez.habittrainer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.fgarcialainez.habittrainer.db.HabitDbTable
import com.fgarcialainez.habittrainer.model.Habit
import kotlinx.android.synthetic.main.activity_create_habit.*
import java.io.IOException

/**
 * CreateHabitActivity
 */
class CreateHabitActivity : AppCompatActivity() {

    private val CHOOSE_IMAGE_REQUEST = 1
    private val TAG = CreateHabitActivity::class.java.simpleName

    private var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_habit)
    }

    fun chooseImage(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        val chooser = Intent.createChooser(intent, "Choose image for habit")
        startActivityForResult(chooser, CHOOSE_IMAGE_REQUEST)

        Log.d(TAG, "Intent to choose image sent...")
    }

    fun storeHabit(view: View) {
        // Validate entered data
        if(et_title.isBlank() || et_description.isBlank()) {
            Log.d(TAG, "No habit stored: title or description missing.")
            displayErrorMessage("Please enter a title and description")
            return
        }
        else if(imageBitmap == null) {
            Log.d(TAG, "No habit stored: image missing.")
            displayErrorMessage("Please choose an image")
            return
        }

        // Store the habit...
        val title = et_title.text.toString()
        val description = et_description.text.toString()
        val habit = Habit(title, description, imageBitmap!!)

        val id = HabitDbTable(this).store(habit)

        if(id == -1L) {
            // Display an error message
            displayErrorMessage("There has been an error storing the habit")
        }
        else {
            // Close the Activity
            finish()
        }
    }

    private fun readBitmap(data: Uri): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(contentResolver, data)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text = message
        tv_error.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CHOOSE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let {
                Log.d(TAG, "An image was chosen by the user")

                val bitmap = readBitmap(it)

                bitmap?.let {
                    imageBitmap = bitmap
                    iv_image.setImageBitmap(it)

                    Log.d(TAG, "Read image bitmap and updated image view")
                }
            }

        }
    }
}

/**
 * EditText Extension Function
 */
private fun EditText.isBlank() = this.text.toString().isBlank()