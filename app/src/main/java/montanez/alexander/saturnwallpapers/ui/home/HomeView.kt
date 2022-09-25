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
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeView : Fragment() {
    private var _binding: FragmentHomeViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModel()
    private var wallpaperBitmap : Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeViewBinding.inflate(inflater, container, false)
        observeLiveData()
        showViewContent(false)
        showLoading(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentAstronomicPhotoOfTheDay()

        binding.homeMoreButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeView_to_homeDescriptionView)
        }
        binding.homeButtonSettings.setOnClickListener{
            findNavController().navigate(R.id.action_homeView_to_settingsView)
        }
        binding.homeManuallyRunButton.setOnClickListener {
            //validate not null
            viewModel.updateWallpaper(wallpaperBitmap!!)
        }
    }

    private fun observeLiveData(){
        val context = this.context
        if (context != null){
            viewModel.astronomicPhoto.observe(viewLifecycleOwner) {
                binding.homeTitle.text = it.title.toString()
                binding.homeAuthor.text = context.getText(R.string.home_author_prefix).toString().plus(" "+it.author)
                binding.homeDate.text = it.date.getReadableString()
                binding.backgroundImage.setImageBitmap(it.picture)
                wallpaperBitmap = it.picture
                showLoading(false)
                showViewContent(true)
                binding.homeAuthor.visibility = if(it.author == null) View.INVISIBLE else View.VISIBLE
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
    }

    private fun showLoading(show: Boolean){
        val visibility = if(show) View.VISIBLE else View.INVISIBLE
        binding.loadingCircle.visibility = visibility
        binding.homeTextLoading.visibility = visibility
    }



}