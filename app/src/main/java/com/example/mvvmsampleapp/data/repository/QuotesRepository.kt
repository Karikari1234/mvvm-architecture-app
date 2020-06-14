package com.example.mvvmsampleapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.Quote
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.SafeApiRequest
import com.example.mvvmsampleapp.data.network.responses.AuthResponse
import com.example.mvvmsampleapp.util.Coroutines
import retrofit2.Response
import java.net.PasswordAuthentication

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    private suspend fun fetchQuotes(){
        if( fetchNeeded()){
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun fetchNeeded(): Boolean{
        return true
    }

    private  fun saveQuotes(quotes: List<Quote>){
        Coroutines.io{
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }


}