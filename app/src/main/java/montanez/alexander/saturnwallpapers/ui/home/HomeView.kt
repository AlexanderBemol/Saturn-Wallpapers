package montanez.alexander.saturnwallpapers.ui.home

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.FragmentHomeViewBinding
import montanez.alexander.saturnwallpapers.databinding.FragmentSettingsViewBinding
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
    ): View? {
        _binding = FragmentHomeViewBinding.inflate(inflater, container, false)
        observeLiveData()
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
            viewModel.updateWallpaper(wallpaperBitmap!!)
        }

        context?.let { wallpaperBitmap = AppCompatResources.getDrawable(it,R.drawable.sample_wallpaper)!!.toBitmap() }
    }

    private fun observeLiveData(){
        val context = this.context
        if (context != null){
            viewModel.astronomicPhoto.observe(viewLifecycleOwner) {
                binding.homeTitle.text = it.title.toString()
                binding.homeAuthor.text = context.getText(R.string.home_author_prefix).toString().plus(" "+it.author)
                binding.homeAuthor.visibility = if(it.author == null) View.INVISIBLE else View.VISIBLE
                binding.homeDate.text = it.date.getReadableString()
                binding.backgroundImage.setImageBitmap(it.picture)
                wallpaperBitmap = it.picture
            }
        }
    }



}