package com.example.user.footballmatchschedule.home

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.user.footballmatchschedule.event.LastMatchFragment
import com.example.user.footballmatchschedule.event.NextMatchFragment
import com.example.user.footballmatchschedule.R
import com.example.user.footballmatchschedule.R.id.*
import com.example.user.footballmatchschedule.favorites.FavoriteTeamsFragment
import org.jetbrains.anko.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("Username", Context.MODE_PRIVATE)
        val Username = sharedPreferences.getString("Username","")
        toast("Welcome "+Username)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                lastmatch ->{
                    loadLastMatchFragment(savedInstanceState)
                }
                nextmatch ->{
                    loadNextMatchFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = lastmatch
    }

    private fun loadLastMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, LastMatchFragment(), LastMatchFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, NextMatchFragment(), NextMatchFragment::class.java.simpleName)
                    .commit()
        }
    }


    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.java.simpleName)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            logout_item ->{
                alert("Do You Want to Logout?", "Logout") {
                    yesButton {
                        startActivity(intentFor<LoginActivity>())
                        finish()
                    }
                    noButton {}
                }.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}
