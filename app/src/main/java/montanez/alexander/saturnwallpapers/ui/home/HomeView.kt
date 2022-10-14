package montanez.alexander.saturnwallpapers.ui.home

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.FragmentHomeViewBinding
import montanez.alexander.saturnwallpapers.utils.getReadableString
import montanez.alexander.saturnwallpapers.utils.getWithFixedSize
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class HomeView : Fragment() {
    private var _binding: FragmentHomeViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by sharedViewModel()
    private var wallpaperBitmap : Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeViewBinding.inflate(inflater, container, false)
        showViewContent(false)
        showInternetError(false)
        showLoading(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        observeLiveData()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.starWorker()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAstronomicPhotoOfTheDay()

        binding.homeMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_global_moreOptionsBottomSheetView)
        }
        binding.homeButtonSettings.setOnClickListener{
            findNavController().navigate(R.id.action_homeView_to_settingsView)
        }
    }

    private fun observeLiveData(){
        val context = this.context
        if (context != null){
            viewModel.astronomicLiveData.observe(this.viewLifecycleOwner){
                val fixedBitmap = it.picture?.getWithFixedSize()
                binding.homeTitle.text = it.title.toString()
                binding.homeAuthor.text = context.getText(R.string.home_author_prefix).toString().plus(" "+it.author)
                binding.homeDate.text = Date().getReadableString()
                binding.backgroundImage.setImageBitmap(fixedBitmap)
                wallpaperBitmap = fixedBitmap
                showLoading(false)
                showInternetError(false)
                showViewContent(true)
                binding.homeAuthor.visibility = if(it.author == null || it.author.equals(""))
                    View.INVISIBLE else View.VISIBLE
            }
            viewModel.noInternetConnectionState.observe(this.viewLifecycleOwner){
                if(it){
                    showLoading(false)
                    showViewContent(false)
                    showInternetError(true)
                }
            }
        }


    }

    private fun showViewContent(show: Boolean){
        val visibility = if(show) View.VISIBLE else View.INVISIBLE
        binding.homeTitle.visibility = visibility
        binding.homeAuthor.visibility = visibility
        binding.homeDate.visibility = visibility
        binding.homeButtonSettings.visibility = visibility
        binding.homeMoreButton.visibility = visibility
        binding.backgroundImage.visibility = visibility
        binding.homeGradient.visibility = visibility
    }

    private fun showLoading(show: Boolean){
        val visibility = if(show) View.VISIBLE else View.INVISIBLE
        binding.loadingAnimation.visibility = visibility
        binding.homeTextLoading.visibility = visibility
    }

    private fun showInternetError(show: Boolean){
        val visibility = if(show) View.VISIBLE else View.INVISIBLE
        binding.noInternetAnimation.visibility = visibility
        binding.homeTextInternet.visibility = visibility
    }



}