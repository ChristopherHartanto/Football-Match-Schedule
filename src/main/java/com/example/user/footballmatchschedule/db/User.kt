package com.example.user.footballmatchschedule.db

data class User (val id: Long?,val Username : String?,val Password : String?,val Email : String?) {
    companion object {
        const val TABLE_USERS: String = "TABLE_USERS"
        const val ID: String = "ID_"
        const val USERNAME: String = "USERNAME"
        const val PASSWORD: String = "PASSWORD"
        const val EMAIL: String = "EMAIL"

    }
}