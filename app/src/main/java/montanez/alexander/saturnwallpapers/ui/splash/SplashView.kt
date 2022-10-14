package montanez.alexander.saturnwallpapers.ui.splash

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.*
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.FragmentSplashViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashView : Fragment() {
    private var _binding: FragmentSplashViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashViewBinding.inflate(inflater, container, false)
        observeLiveData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        MainScope().launch {
            delay(1500L)
            viewModel.checkOnBoardingStatus()
        }
    }

    private fun observeLiveData(){
        val context = this.context
        if (context != null){
            viewModel.wasOnBoardingCompleted.observe(viewLifecycleOwner) {
                if(it) findNavController().navigate(R.id.action_splashView_to_homeView)
                else findNavController().navigate(R.id.action_splashView_to_onBoardingView)
            }
        }
    }

}