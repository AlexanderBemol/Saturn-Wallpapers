package montanez.alexander.saturnwallpapers.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.progressindicator.CircularProgressIndicatorSpec
import com.google.android.material.progressindicator.IndeterminateDrawable
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.DialogMoreOptionsBinding
import montanez.alexander.saturnwallpapers.model.ScreenOfWallpaper
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreOptionsBottomSheetView : BottomSheetDialogFragment() {
    private var _binding: DialogMoreOptionsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogMoreOptionsBinding.inflate(inflater, container, false)
        observeLiveData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.moreOptionsDownload.setOnClickListener {
            onLoading(it as MaterialTextView)
            viewModel.downloadPhoto()
        }
        binding.moreOptionsSetBoth.setOnClickListener {
            viewModel.updateSpecificWallpaper(ScreenOfWallpaper.BOTH_SCREENS)
            onLoading(it as MaterialTextView)
        }
        binding.moreOptionsSetLock.setOnClickListener {
            viewModel.updateSpecificWallpaper(ScreenOfWallpaper.LOCK_SCREEN)
            onLoading(it as MaterialTextView)
        }
        binding.moreOptionsSetHome.setOnClickListener {
            viewModel.updateSpecificWallpaper(ScreenOfWallpaper.HOME_SCREEN)
            onLoading(it as MaterialTextView)
        }
    }

    private fun onLoading(textView: MaterialTextView){
        val myContext = context
        if(myContext != null){
            val circularProgressIndicatorSpec =
                CircularProgressIndicatorSpec(
                    myContext,null, 0,
                    com.google.android.material.R.style.Widget_Material3_CircularProgressIndicator_ExtraSmall)

            val progressIndicatorDrawable =
                IndeterminateDrawable.createCircularDrawable(myContext, circularProgressIndicatorSpec)

            progressIndicatorDrawable.setTint(R.attr.colorPrimary)
            progressIndicatorDrawable.start()
            progressIndicatorDrawable.setVisible(true,true)

            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                progressIndicatorDrawable,null,null,null)

        }

    }

    private fun onEventCompleted(){
        val myContext = context
        if(myContext != null){
            val downloadDrawable = AppCompatResources.getDrawable(myContext,R.drawable.ic_save)
            val bothDrawable = AppCompatResources.getDrawable(myContext,R.drawable.ic_wallpaper)
            val lockDrawable = AppCompatResources.getDrawable(myContext,R.drawable.ic_lock_screen)
            val homeDrawable = AppCompatResources.getDrawable(myContext,R.drawable.ic_home)

            binding.moreOptionsDownload.setCompoundDrawablesRelativeWithIntrinsicBounds(
                downloadDrawable,null,null,null
            )
            binding.moreOptionsSetBoth.setCompoundDrawablesRelativeWithIntrinsicBounds(
                bothDrawable,null,null,null
            )
            binding.moreOptionsSetHome.setCompoundDrawablesRelativeWithIntrinsicBounds(
                homeDrawable,null,null,null
            )
            binding.moreOptionsSetLock.setCompoundDrawablesRelativeWithIntrinsicBounds(
                lockDrawable,null,null,null
            )
        }
        this.dismiss()
    }

    private fun observeLiveData(){
        val context = this.context
        if (context != null){
            lifecycleScope.launch {
                viewModel.eventState.flowWithLifecycle(
                    this@MoreOptionsBottomSheetView.lifecycle, Lifecycle.State.STARTED
                )
                    .collect {onEventCompleted()}
            }
        }
    }
}