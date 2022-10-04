package montanez.alexander.saturnwallpapers.ui.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.FragmentSettingsViewBinding
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.model.QualityOfImages
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsView : Fragment() {
    private var _binding: FragmentSettingsViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsViewBinding.inflate(inflater, container, false)
        viewModel.getSettingValues()
        observeLiveData()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingsBackButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.spinnerQuality.setOnClickListener {
            showMenu(it, R.menu.quality_menu)
        }
        binding.spinnerScreen.setOnClickListener {
            showMenu(it, R.menu.screens_menu)
        }
        binding.switchServiceEnabled.setOnCheckedChangeListener { _, b ->
            viewModel.setIsServiceEnabled(b)
            if(!b) cancelService()
        }

    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener {
             when (menuRes){
                 R.menu.quality_menu -> {
                    binding.spinnerQuality.text = it.title
                     viewModel.setQualityOfImages(
                         if(it.title  == getString(R.string.quality_high)) QualityOfImages.HIGH_QUALITY
                         else QualityOfImages.NORMAL_QUALITY
                     )
                 }
                 else -> {
                     binding.spinnerScreen.text = it.title
                     viewModel.setScreenOfWallpaper(
                         when(it.title){
                             getString(R.string.screens_home) -> ScreenOfWallpaper.HOME_SCREEN
                             getString(R.string.screens_lock) -> ScreenOfWallpaper.LOCK_SCREEN
                             else -> ScreenOfWallpaper.BOTH_SCREENS
                         }
                     )
                 }
             }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun observeLiveData(){
        val context = context
        if(context != null){
            viewModel.settingsServiceValues.observe(viewLifecycleOwner){
                binding.switchServiceEnabled.isChecked = it.isServiceEnabled

                if (it.qualityOfImages == QualityOfImages.HIGH_QUALITY) binding.spinnerQuality.text = getString(R.string.quality_high)
                else binding.spinnerQuality.text = getString(R.string.quality_normal)

                when (it.screenOfWallpaper) {
                    ScreenOfWallpaper.HOME_SCREEN -> binding.spinnerScreen.text = getString(R.string.screens_home)
                    ScreenOfWallpaper.LOCK_SCREEN -> binding.spinnerScreen.text = getString(R.string.screens_lock)
                    else -> binding.spinnerScreen.text = getString(R.string.screens_both)
                }
            }
        }
    }

    private fun cancelService(){
        context?.let { WorkManager.getInstance(it).cancelAllWorkByTag(Constants.WORK_MANAGER_DAILY_TAG) }
    }

}