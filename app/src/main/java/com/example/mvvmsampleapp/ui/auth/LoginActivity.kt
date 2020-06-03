package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.repository.UserRepository
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.ui.home.HomeActivity
import com.example.mvvmsampleapp.util.hide
import com.example.mvvmsampleapp.util.show
import com.example.mvvmsampleapp.util.toast
import com.example.mvvmsampleapp.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),AuthListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = MyApi()
        val db = AppDatabase(this)
        val repository = UserRepository(api,db)
        val factory = AuthViewModelFactory(repository)

        val binding: ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if(user != null){
                Intent(this,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(it)
                }
            }
        })
    }

    override fun onStarted() {
        progress_bar.show()

    }

    override fun onSuccess(user: User) {
        progress_bar.hide()
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }
}
