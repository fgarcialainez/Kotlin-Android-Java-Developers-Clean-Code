package com.fgarcialainez.habittrainer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set data
        iv_icon.setImageResource(R.drawable.water)
        tv_title.setText(getString(R.string.drink_water_title))
        tv_description.setText(getString(R.string.drink_water_description))
    }
}