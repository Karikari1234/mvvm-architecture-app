package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.data.repository.UserRepository
import com.example.mvvmsampleapp.util.ApiException
import com.example.mvvmsampleapp.util.Coroutines
import com.example.mvvmsampleapp.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
): ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    var authListener:AuthListener? = null
    var passwordConfirmation: String? = null

    fun getLoggedInUser() = repository.getUser()
    fun onLoginButtonClicked(view: View){
        authListener!!.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            //
            authListener!!.onFailure("Invalid email or password")
            return
        }
        Coroutines.main {
            try {
                val response = repository.userLogin(email!!,password!!)
                response.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(response.message!!)

            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onSignupButtonClicked(view: View){
        authListener!!.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty() ||name.isNullOrEmpty() ||passwordConfirmation.isNullOrEmpty()){
            authListener!!.onFailure("Invalid email or password")
            return
        }
        if(passwordConfirmation != password){
            authListener!!.onFailure("Passwords does not match")
            return
        }
        Coroutines.main {
            try {
                val response = repository.userSignup(name!!,email!!,password!!)
                response.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(response.message!!)

            }catch (e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onSignup(view: View){
        Intent(view.context,SignupActivity::class.java).also{
            view.context.startActivity(it)
        }
    }

    fun onLogin(view: View){
        Intent(view.context,LoginActivity::class.java).also{
            view.context.startActivity(it)
        }
    }
}