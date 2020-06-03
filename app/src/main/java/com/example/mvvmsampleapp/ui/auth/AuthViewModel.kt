package com.example.mvvmsampleapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.data.repository.UserRepository
import com.example.mvvmsampleapp.util.ApiException
import com.example.mvvmsampleapp.util.Coroutines

class AuthViewModel: ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener:AuthListener? = null

    fun onLoginButtonClicked(view: View){
        authListener!!.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            //
            authListener!!.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                val response = UserRepository().userLogin(email!!,password!!)
                response.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(response.message!!)

            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }
        }
    }
}