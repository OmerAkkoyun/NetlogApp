package com.example.xooicase.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.xooicase.R
import com.example.xooicase.common.Constants
import com.example.xooicase.databinding.FragmentSplashBinding
import com.example.xooicase.presentation.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    companion object {
        fun newInstance() = SplashFragment()
    }

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)

        val slideAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.side_slide)
        binding.imgSplash.startAnimation(slideAnimation)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // wait 2 seconds for splash
        lifecycleScope.launch {
            delay(2000)

            // prepare details data
            viewModel.getDetails()
        }


        viewModel.detailsLiveData.observe(this) {
            val bundle = Bundle()
            bundle.putSerializable(Constants.DETAILS_KEY, it)
            findNavController().navigate(R.id.action_splashFragment_to_detailsFragment, bundle)
        }

    }


}