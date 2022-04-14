package com.example.coursework

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aboutButton = findViewById<Button>(R.id.aboutButton)

        aboutButton.setOnClickListener{
            aboutButton.isVisible = false       //hiding the about button on click
            val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popup_window,null)

            val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            val exitPopUpButton = view.findViewById<Button>(R.id.buttonExit)
            exitPopUpButton.setOnClickListener {
                popupWindow.dismiss()
                aboutButton.isVisible = true    //displaying the about button on popup window close
            }

            val rootLayout = findViewById<LinearLayout>(R.id.root_layout)       //displaying the pop up window
            TransitionManager.beginDelayedTransition(rootLayout)
            popupWindow.showAtLocation(
                rootLayout,
                Gravity.CENTER,
                0,
                0
            )
        }
        //starting a new activity
        val newGameButton = findViewById<Button>(R.id.newGameButton)
        newGameButton.setOnClickListener {
            val contactIntent = Intent(this, NewGame::class.java)
            startActivity(contactIntent)
        }
    }
}