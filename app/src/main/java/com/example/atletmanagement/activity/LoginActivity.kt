package com.example.atletmanagement.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atletmanagement.databinding.ActivityLoginBinding
import com.example.atletmanagement.global.DB
import com.example.atletmanagement.manager.SessionManager

class LoginActivity : AppCompatActivity() {
    private var db: DB? = null
    private var session: SessionManager? = null

    private var edtUserName: EditText? = null
    private var edtPassword: EditText? = null
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        session = SessionManager(this)
        edtUserName = binding.edtUserName
        edtPassword = binding.edtPassword

        binding.btnLogin.setOnClickListener {
            if (validateLogin()) {
                getLogin()
            }
        }

        binding.txtForgotPassword.setOnClickListener {

        }

    }

    private fun getLogin() {
        try {

            val sqlQuery = "SELECT * FROM ADMIN WHERE USER_NAME='" + edtUserName?.text.toString()
                .trim() + "' " +
                    "AND PASSWORD = '" + edtPassword?.text.toString().trim() + "' AND ID='1'"
            db?.fireQuery(sqlQuery)?.use {
                if (it.count > 0) {
                    session?.setLogin(true)
                    Toast.makeText(this, "Successfully Log In", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    session?.setLogin(false)
                    Toast.makeText(this, "Log In failed", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validateLogin(): Boolean {
        if (edtUserName?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter User Name", Toast.LENGTH_SHORT).show()
            return false
        } else if (edtPassword?.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}