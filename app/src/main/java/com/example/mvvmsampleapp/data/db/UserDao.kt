package com.example.mvvmsampleapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmsampleapp.data.db.entities.CURRENT_USER
import com.example.mvvmsampleapp.data.db.entities.User

interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User): Long

    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER")
    fun getUser(): LiveData<User>

}