package com.irrelevxnce.docklandsacademy

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class LoginPage : AppCompatActivity() {

    private lateinit var loadingCircle: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var darken: TextView
    private lateinit var emailBox: EditText
    private lateinit var passBox: EditText
    private lateinit var email: String
    private lateinit var secret: String
    private var correctCredentials: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        loginButton = findViewById(R.id.LoginButton)
        loadingCircle = findViewById(R.id.progressBar)
        emailBox = findViewById(R.id.user)
        passBox = findViewById(R.id.password)
        darken = findViewById(R.id.darken)
        loginButton.setOnClickListener(this :: logIn)
    }

    fun connectToDatabase(): Connection {
        val url = "jdbc:mysql://108.163.225.126:3306/bespokes_dockinvoice"
        val user = "bespokes_camdev"
        val password = "M0b1l3!APP"
        return DriverManager.getConnection(url, user, password)
    }

    private inner class LoginTask : AsyncTask<Void, Void, Boolean>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: Void): Boolean {
            email = emailBox.text.toString()
            secret = passBox.text.toString()
            val dbConnection: Connection = connectToDatabase()
            val statement: Statement = dbConnection.createStatement()
            val sql = "SELECT * FROM ip_users WHERE user_email = '$email'"
            val resultSet: ResultSet = statement.executeQuery(sql)

            var hasCorrectCredentials = false

            while (resultSet.next()) {
                try {
                    val columnValue = resultSet.getString("user_name")
                    Toast.makeText(this@LoginPage, columnValue, Toast.LENGTH_SHORT).show()
                    hasCorrectCredentials = true
                } catch (e: Exception) {
                    Toast.makeText(this@LoginPage, "email not found", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                } finally {
                    resultSet.close()
                    statement.close()
                    dbConnection.close()
                }
            }

            return hasCorrectCredentials
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(hasCorrectCredentials: Boolean) {
            darken.visibility = View.VISIBLE
            loadingCircle.visibility = View.VISIBLE

            val intent = Intent(this@LoginPage, LandingPage::class.java)
            if (hasCorrectCredentials) {
                startActivity(intent)
            } else {
                darken.visibility = View.INVISIBLE
                loadingCircle.visibility = View.GONE
            }
        }
    }

    private fun logIn(view: View?) {
        LoginTask().execute()
    }
}