package com.example.mvvmsampleapp.ui.auth

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: Unit)
    fun onFailure(message: String)
}