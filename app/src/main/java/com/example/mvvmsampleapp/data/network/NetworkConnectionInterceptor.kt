package com.example.mvvmsampleapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.example.mvvmsampleapp.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context): Interceptor {

    private val applicationContext = context.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isInternetAvailable())
            throw NoInternetException("Internet is not available. please try later.")

        return chain.proceed(chain.request())
    }
    private fun isInternetAvailable(): Boolean{
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetwork.also {
            return it != null
        }
    }
}