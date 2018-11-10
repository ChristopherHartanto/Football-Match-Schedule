package com.example.user.footballmatchschedule.event

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import com.example.user.footballmatchschedule.api.ApiRepository
import com.example.user.footballmatchschedule.model.Event
import com.example.user.footballmatchschedule.R
import com.example.user.footballmatchschedule.R.id.add_to_favorite
import com.example.user.footballmatchschedule.api.TheSportDBApi
import com.example.user.footballmatchschedule.db.Favorite
import com.example.user.footballmatchschedule.db.database
import com.example.user.footballmatchschedule.db.User
import com.example.user.footballmatchschedule.detail.MainPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_view_event.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

class ViewEvent : AppCompatActivity(){

    private var idevent : String = ""
    private lateinit var events : Event
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)


        val request = ApiRepository()
        val gson = Gson()
        supportActionBar?.title = "Event Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        idevent = intent.getStringExtra("idEvent")
        favoriteState()
        getEventList3(idevent,request,gson)

        ivHome.onClick {
            startActivity(intentFor<TeamDetail>("Team" to events.HomeTeam))
        }

        ivAway.onClick {
            startActivity(intentFor<TeamDetail>("Team" to events.AwayTeam))
        }
    }

    private fun getEventList3(idevent : String, apiRepository: ApiRepository, gson: Gson) {

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getEventtt(idevent)),
                    ER::class.java
            )

            uiThread {
                getData(data.events)
                getBadge(data.events[0].HomeTeam,apiRepository,gson,ivHome)
                getBadge(data.events[0].AwayTeam,apiRepository,gson,ivAway)
            }
        }
    }

    private fun getBadge(team : String?, apiRepository: ApiRepository, gson: Gson, teamBadge : ImageView) {
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getBadge(team)),
                    GB::class.java
            )

            uiThread {
                getImage(data.teams,teamBadge)
            }
        }
    }
// 11 Juli 2018
    private fun getData(event: List<Event>) {
        events = Event(event[0].idevent, event[0].DateEvent, event[0].HomeScore, event[0].AwayScore, event[0].HomeTeam, event[0].AwayTeam,
                event[0].HomeGoal, event[0].AwayGoal)
        tvDate.text = SimpleDateFormat("EEE, dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(event[0].DateEvent))
        tvHomeScore.text = event[0].HomeScore
        tvAwayScore.text = event[0].AwayScore
        tvHome.text = event[0].HomeTeam
        tvAway.text = event[0].AwayTeam
        homegoal.text = event[0].HomeGoal
        awaygoal.text = event[0].AwayGoal
        //
        homeformation.text = event[0].HomeFormation
        awayformation.text = event[0].AwayFormation
        homelinegoalkeeper.text = event[0].HomeLineupGoalkeeper
        awaylinegoalkeeper.text = event[0].AwayLineupGoalkeeper
        homelineupdefense.text = event[0].HomeLineupDefense
        awaylineupdefense.text = event[0].AwayLineupDefense
        homelineupforward.text = event[0].HomeLineupForward
        awaylineupforward.text = event[0].AwayLineupForward
        homelineupmidfield.text = event[0].HomeLineupMidfield
        awaylineupmidfield.text = event[0].AwayLineupMidfield
        homelineupsubstitutes.text = event[0].HomeLineupSubstitutes
        awaylineupsubstitutes.text = event[0].AwayLineupSubstitutes
        homeredcards.text = event[0].HomeRedCards
        awayredcards.text = event[0].AwayRedCards
        homeyellowcards.text = event[0].HomeYellowCards
        awayyellowcards.text = event[0].AwayYellowCardsl
        homeshots.text = event[0].HomeShots
        awayshots.text = event[0].AwayShots

    }

    private fun getImage(teams: List<Event>, teamBadge : ImageView){
        Picasso.get().load(teams.get(0).teamBadge).into(teamBadge)
    }


    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {idevent})",
                            "idevent" to idevent)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if(::events.isInitialized){
                    val sharedPreferences = getSharedPreferences("Username", Context.MODE_PRIVATE)
                    val Username = sharedPreferences.getString("Username","")

                    if (isFavorite) removeFromFavorite(Username) else addToFavorite(Username)

                    isFavorite = !isFavorite
                    setFavorite()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(Username : String){

        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to events.idevent,
                        Favorite.USERNAME to Username,
                        Favorite.DATE_EVENT to events.DateEvent,
                        Favorite.HOME_NAME to events.HomeTeam,
                        Favorite.AWAY_NAME to events.AwayTeam,
                        Favorite.HOME_SCORE to events.HomeScore,
                        Favorite.AWAY_SCORE to events.AwayScore)
            }
            toast("Added to Favorite")
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite(Username: String){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id}) AND (USERNAME = {Username})"
                        , "id" to idevent, "Username" to Username)
            }
            toast("Removed to Favorite")
        } catch (e: SQLiteConstraintException){
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }


    class ER (
            val events: List<Event>)

    class GB (
            val teams: List<Event>)
}
