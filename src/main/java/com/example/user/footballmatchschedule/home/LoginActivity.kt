package com.example.user.footballmatchschedule.home

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.user.footballmatchschedule.R
import com.example.user.footballmatchschedule.db.User
import com.example.user.footballmatchschedule.db.database
import org.jetbrains.anko.db.select
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("Username", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        btnLogin.onClick {
            val Username = etUsername.text.toString()
            val Password = etPassword.text.toString()
            if(Username.equals("") || Password.equals("")) toast("Username and Password Must be Filled!!")
            else if(check()){
                editor.putString("Username", etUsername.text.toString())
                editor.apply()
                startActivity(intentFor<MainActivity>())
                finish()
            }
            else{
                toast("Wrong Username or Password")
                etUsername.setText("")
                etPassword.setText("")
            }
        }

        tvRegister.onClick {
            startActivity(intentFor<RegisterActivity>())
        }
    }

    private fun check(): Boolean {
        var cek = false

        applicationContext.database.use {

            val result = select(User.TABLE_USERS)
                    .whereArgs("(USERNAME = {Username}) AND {Password}",
                            "Username" to etUsername.text.toString(), "Password" to etPassword.text.toString())
            val user = result.parseList(classParser<User>())
            if(!user.isEmpty()) cek = true

        }
        return cek
    }

}
