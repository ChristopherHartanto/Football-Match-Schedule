package com.example.user.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Event(

        @SerializedName("idEvent")
        var idevent: String? = null,

        @SerializedName("dateEvent")
        var DateEvent: String? = null,

        @SerializedName("intHomeScore")
        var HomeScore: String? = null,

        @SerializedName("intAwayScore")
        var AwayScore: String? = null,

        @SerializedName("strHomeTeam")
        var HomeTeam: String? = null,

        @SerializedName("strAwayTeam")
        var AwayTeam: String? = null,

        @SerializedName("strHomeGoalDetails")
        var HomeGoal: String? = null,

        @SerializedName("strAwayGoalDetails")
        var AwayGoal: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,

        @SerializedName("strHomeRedCards")
        var HomeRedCards: String? = null,

        @SerializedName("strAwayRedCards")
        var AwayRedCards: String? = null,

        @SerializedName("strHomeYellowCards")
        var HomeYellowCards: String? = null,

        @SerializedName("AwayYellowCards")
        var AwayYellowCardsl: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var HomeLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var AwayLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var HomeLineupDefense: String? = null,

        @SerializedName("strAwayLineupDefense")
        var AwayLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var HomeLineupMidfield: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var AwayLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var HomeLineupForward: String? = null,

        @SerializedName("strAwayLineupForward")
        var AwayLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var HomeLineupSubstitutes: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var AwayLineupSubstitutes: String? = null,

        @SerializedName("strHomeFormation")
        var HomeFormation: String? = null,

        @SerializedName("strAwayFormation")
        var AwayFormation: String? = null,

        @SerializedName("intHomeShots")
        var HomeShots: String? = null,

        @SerializedName("intAwayShots")
        var AwayShots: String? = null
)