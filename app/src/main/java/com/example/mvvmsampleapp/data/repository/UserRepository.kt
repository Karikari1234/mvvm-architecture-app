package com.example.mvvmsampleapp.data.repository

import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.SafeApiRequest
import com.example.mvvmsampleapp.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository : SafeApiRequest() {
    suspend fun userLogin(email: String,password: String): AuthResponse {

        return apiRequest { MyApi().userLogin(email,password) }
    }
}