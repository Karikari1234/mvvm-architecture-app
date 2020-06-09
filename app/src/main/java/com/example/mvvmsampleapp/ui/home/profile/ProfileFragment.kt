package com.example.mvvmsampleapp.ui.home.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.databinding.ProfileFragmentBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.x.kodein
class ProfileFragment : Fragment(),KodeinAware {

    companion object {
        fun newInstance() = ProfileFragment()
    }
    private lateinit var viewModel: ProfileViewModel
    override val kodein: Kodein by kodein()
    private val factory: ProfileViewModelFactory by instance<ProfileViewModelFactory>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: ProfileFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.profile_fragment,container,false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this,factory).get(ProfileViewModel::class.java)
    }
}