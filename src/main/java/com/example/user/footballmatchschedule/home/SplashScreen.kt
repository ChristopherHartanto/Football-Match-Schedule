package com.example.user.footballmatchschedule.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.user.footballmatchschedule.R
import org.jetbrains.anko.intentFor

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(intentFor<LoginActivity>())
            finish()
        }, 2000)
    }
}
