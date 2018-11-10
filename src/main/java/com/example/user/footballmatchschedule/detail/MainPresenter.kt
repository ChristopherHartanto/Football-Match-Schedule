package com.example.user.footballmatchschedule.detail

import com.example.user.footballmatchschedule.api.ApiRepository
import com.example.user.footballmatchschedule.api.CoroutineContextProvider
import com.example.user.footballmatchschedule.favorites.EventResponse
import com.example.user.footballmatchschedule.api.TheSportDBApi
import com.example.user.footballmatchschedule.favorites.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.jetbrains.anko.coroutines.experimental.bg

class MainPresenter(private val view: MainView,
                    private val apiRepository: ApiRepository,
                    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    companion object {
        var idlingResourceCounter = 1
    }

    fun getEventList(idEvent : String?) {
        view.showLoading()

        launch(context.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.LastMatch(idEvent)),
                        EventResponse::class.java)
            }
            view.showEventList(data.await().events)
            view.hideLoading()
            idlingResourceCounter = 0

        }
    }

    fun getidEvent(League : String?, cek : Int) {

        launch(UI){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getIdEvent(League)),
                        TeamResponse::class.java)
            }
            val id = data.await().teams[0].idLeague
            if(cek == 1) getEventList(id)
            else if (cek ==2) getEventList2(id)

            idlingResourceCounter = 0
        }
    }


    fun getEventList2(idEvent: String?) {
        view.showLoading()

        launch(UI){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.NextMatch(idEvent)),
                        EventResponse::class.java)
            }

            view.showEventList(data.await().events)
            view.hideLoading()

            idlingResourceCounter = 0
        }

    }


}

