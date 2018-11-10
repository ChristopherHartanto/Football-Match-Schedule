package com.example.user.footballmatchschedule.detail

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.footballmatchschedule.R
import com.example.user.footballmatchschedule.R.id.*
import com.example.user.footballmatchschedule.api.ApiRepository
import com.example.user.footballmatchschedule.api.TheSportDBApi
import com.example.user.footballmatchschedule.event.ViewEvent
import org.jetbrains.anko.*
import com.example.user.footballmatchschedule.model.Event
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.cardview.v7.cardView
import java.text.SimpleDateFormat

class MainAdapter(private val events: List<Event>, private val listener: (Event)->Unit)
    : RecyclerView.Adapter<TeamViewHolderr>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolderr {
        return TeamViewHolderr(EventUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: TeamViewHolderr, position: Int) {
        holder.bindItem(events[position],listener)
    }

    override fun getItemCount(): Int = events.size

}

class EventUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            cardView {
                lparams{
                    width = matchParent
                    leftMargin = dip(10)
                    rightMargin = dip(10)
                    bottomMargin = dip(7)
                }

                linearLayout {
                    lparams(width = matchParent, height = dip(50))
                    orientation = LinearLayout.VERTICAL


                    textView {
                        id = R.id.event_date
                        textColor = R.color.colorPrimaryText
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        gravity = Gravity.CENTER
                    }

                    textView {
                        id = R.id.event_name
                        textSize = 18F
                    }.lparams {
                        gravity = Gravity.CENTER
                    }

                }

            }
        }
    }


}

class TeamViewHolderr(val view: View) : RecyclerView.ViewHolder(view){

    private val eventDate: TextView = view.find(event_date)
    private val eventName: TextView = view.find(event_name)


    fun bindItem(events: Event, listener: (Event) -> Unit) {

        eventDate.text = SimpleDateFormat("EEE, dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(events.DateEvent))

        if(events.HomeScore == null && events.AwayScore == null){
            eventName.text = events.HomeTeam + "   " + " vs "+ "   " + events.AwayTeam
        }
        else
            eventName.text = events.HomeTeam + " " + events.HomeScore + " vs "+ events.AwayScore + " " + events.AwayTeam

        view.setOnClickListener{ listener(events)}

    }


}
