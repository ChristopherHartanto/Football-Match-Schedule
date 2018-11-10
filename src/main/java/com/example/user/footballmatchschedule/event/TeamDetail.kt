package com.example.user.footballmatchschedule.event

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.user.footballmatchschedule.R
import com.example.user.footballmatchschedule.api.ApiRepository
import com.example.user.footballmatchschedule.api.TheSportDBApi
import com.example.user.footballmatchschedule.favorites.TeamResponse
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.toast

class TeamDetail : AppCompatActivity() {

    private var Team : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        Team = intent.getStringExtra("Team")

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getTeamDetail(Team)
    }

    fun getTeamDetail(Team : String?) {

        async(UI){
            val data = bg{
                Gson().fromJson(ApiRepository()
                        .doRequest(TheSportDBApi.TeamDetail(Team)),
                        TeamResponse::class.java)
            }
            tvTeamName.text = data.await().teams[0].TeamName
            tvTeamDetail.text = data.await().teams[0].TeamDesc
            Picasso.get().load(data.await().teams[0].TeamBadge).into(ivTeamDetail)
        }
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