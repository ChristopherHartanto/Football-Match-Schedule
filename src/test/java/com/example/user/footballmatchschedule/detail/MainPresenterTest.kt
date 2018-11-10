package com.example.user.footballmatchschedule.detail

import android.util.EventLog
import com.example.user.footballmatchschedule.api.ApiRepository
import com.example.user.footballmatchschedule.api.CoroutineContextProvider
import com.example.user.footballmatchschedule.api.TestContextProvider
import com.example.user.footballmatchschedule.api.TheSportDBApi
import com.example.user.footballmatchschedule.favorites.EventResponse
import com.example.user.footballmatchschedule.model.Event
import com.google.gson.Gson
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @Mock
    private
    lateinit var view: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventList() {

        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val idEvent = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.LastMatch(idEvent)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventList(idEvent)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showEventList(events)
        Mockito.verify(view).hideLoading()

    }
}