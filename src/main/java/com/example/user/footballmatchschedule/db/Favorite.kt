package com.example.user.footballmatchschedule.db

data class Favorite(val id: Long?, val idEvent: String?,val Username : String?, val dateEvent: String?, val homeName: String?,
                    val awayName: String?,val homeScore: String?, val awayScore: String?) {
    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val ID: String = "ID_"
        const val USERNAME: String = "USERNAME"
        const val EVENT_ID: String = "EVENT_ID"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val HOME_NAME: String = "HOME_NAME"
        const val AWAY_NAME: String = "AWAY_NAME"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}