package com.example.mvvmsampleapp.data.db.entities

import androidx.room.Entity

@Entity
data class User(
    var Id: Int? = null,
    var name: String? =null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at:String? = null,
    var created_at:String? =null,
    var updated_at:String? = null
)