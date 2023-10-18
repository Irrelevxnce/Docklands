package com.irrelevxnce.docklandsacademy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginPage : AppCompatActivity() {

    private lateinit var loadingCircle: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var darken: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        loginButton = findViewById(R.id.LoginButton)
        loadingCircle = findViewById(R.id.progressBar)
        darken = findViewById(R.id.darken)
        loginButton.setOnClickListener(this :: logIn)
    }

    private fun logIn(view: View?) {
        runOnUiThread {
            darken.visibility = View.VISIBLE
            loadingCircle.visibility = View.VISIBLE
            val intent = Intent(this, LandingPage::class.java)
            startActivity(intent)
        }
    }
}