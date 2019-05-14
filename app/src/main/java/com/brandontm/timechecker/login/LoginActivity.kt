package com.brandontm.timechecker.login

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.brandontm.timechecker.R
import com.brandontm.timechecker.timecheck.TimeCheckActivity
import com.brandontm.timechecker.entities.User
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.getUsers().observe(this, Observer<List<User>> {
            users -> login(users)
        })

        val passwordInputLayout = findViewById<TextInputLayout>(R.id.passwordInputLayout)

        val txtUsername = findViewById<EditText>(R.id.txtUser)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                passwordInputLayout.error = null
            }

        }
        txtUsername.addTextChangedListener(textWatcher)
        txtPassword.addTextChangedListener(textWatcher)
    }

    private fun login(users: List<User>?) {
        // TODO: Replace login
        val username = findViewById<EditText>(R.id.txtUser).text.toString()
        val password = findViewById<EditText>(R.id.txtPassword).text.toString()

        if(username.isEmpty() && password.isEmpty()) return

        val user = users?.find { it.username == username && it.password == password }

        if (user != null) {
            intent = Intent(this@LoginActivity, TimeCheckActivity::class.java)
            intent.putExtra("userId", user.id)
            startActivity(intent)
        } else {
            findViewById<TextInputLayout>(R.id.passwordInputLayout).error =
                    "Contraseña incorrecta"
        }
    }


    fun onContinueClicked(v: View) {
        viewModel.getUsers()
    }
}
