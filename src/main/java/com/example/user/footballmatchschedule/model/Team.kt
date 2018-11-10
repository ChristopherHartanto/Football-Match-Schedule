package com.example.user.footballmatchschedule.model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idLeague")
        var idLeague: String? = null,

        @SerializedName("strTeamBadge")
        var TeamBadge: String? = null,

        @SerializedName("strDescriptionEN")
        var TeamDesc: String? = null,

        @SerializedName("strTeam")
        var TeamName: String? = null

)


