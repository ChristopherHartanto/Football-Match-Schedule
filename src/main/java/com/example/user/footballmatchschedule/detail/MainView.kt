package com.example.user.footballmatchschedule.detail

import com.example.user.footballmatchschedule.model.Event
import com.example.user.footballmatchschedule.model.Team

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showEventList(data: List<Event>)
}