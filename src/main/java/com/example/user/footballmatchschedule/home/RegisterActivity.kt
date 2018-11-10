package com.example.user.footballmatchschedule.home

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.user.footballmatchschedule.R

import com.example.user.footballmatchschedule.db.User
import com.example.user.footballmatchschedule.db.database
import org.jetbrains.anko.*
import org.jetbrains.anko.db.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnRegister.onClick {
            val Username = etUsername.text.toString()
            val Password = etPassword.text.toString()
            val Email = etEmail.text.toString()

            if(Username.equals("")) etUsername.error = "Please Fill Username"
            if(Password.equals("")) etPassword.error = "Please Fill Password"
            if(Email.equals("")) etEmail.error = "Please Fill Email"
            if(!Username.equals("")
                    && !Password.equals("")
                    && !Email.equals("")
                    && !checkUsername(Username)) etUsername.error = "Username Already RegisterActivity"
            if(!Email.equals("")
                    && !isEmailValid(Email)) etEmail.error = "Please Fill Valid Email"
            if(!Username.equals("")
                    && !Password.equals("")
                    && !Email.equals("")
                    && checkUsername(Username)
                    && isEmailValid(Email)){
                try {
                    database.use {
                        insert(User.TABLE_USERS,
                                User.USERNAME to etUsername.text.toString(),
                                User.PASSWORD to etPassword.text.toString(),
                                User.EMAIL to etEmail.text.toString())
                    }
                    alert("Go to Login Page", "RegisterActivity Success!!") {
                        yesButton {
                            startActivity(intentFor<LoginActivity>())
                            finish()
                        }
                        noButton {}
                    }.show()

                } catch (e: SQLiteConstraintException){
                    toast(e.localizedMessage)
                }
            }

        }

        tvBackLogin.onClick {
            startActivity(intentFor<LoginActivity>())
            finish()
        }

    }

    private fun checkUsername(Username : String) : Boolean{
        var cek = false

        applicationContext.database.use {

            val result = select(User.TABLE_USERS)
                    .whereArgs("(USERNAME = {Username})",
                            "Username" to Username)
            if(result.parseList(classParser<User>()).isEmpty()) cek = true
        }
        return cek
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
