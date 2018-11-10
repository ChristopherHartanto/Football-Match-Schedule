package com.example.user.footballmatchschedule.favorites

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.footballmatchschedule.db.Favorite
import com.example.user.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat

/**
 * Created by root on 1/16/18.
 */
class FavoriteTeamsAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit)
    : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

    override fun getItemCount(): Int = favorite.size

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {

            cardView {
                lparams {
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
                        textSize = 14f
                        textColor = R.color.colorPrimaryDark
                        typeface = Typeface.DEFAULT_BOLD

                    }.lparams {
                        gravity = Gravity.CENTER
                    }

                    textView {
                        id = R.id.event_name
                        textSize = 18f
                    }.lparams {
                        gravity = Gravity.CENTER
                    }

                }
            }
        }
    }

}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val eventDate: TextView = view.find(R.id.event_date)
    private val eventName: TextView = view.find(R.id.event_name)

    fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit) {

        eventDate.text = SimpleDateFormat("EEE, dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(favorite.dateEvent))
        if(favorite.homeScore == null && favorite.awayScore == null){
            eventName.text = favorite.homeName + "   " + " vs "+ "   " + favorite.awayName
        }
        else
            eventName.text = favorite.homeName  + " " + favorite.homeScore + " vs "+ favorite.awayScore + " " + favorite.awayName

        itemView.onClick { listener(favorite) }
    }
}