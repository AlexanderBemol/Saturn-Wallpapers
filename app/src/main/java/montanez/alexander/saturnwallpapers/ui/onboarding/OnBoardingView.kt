package montanez.alexander.saturnwallpapers.ui.onboarding

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import montanez.alexander.saturnwallpapers.R
import montanez.alexander.saturnwallpapers.databinding.FragmentOnboadingViewBinding
import montanez.alexander.saturnwallpapers.model.Constants
import montanez.alexander.saturnwallpapers.model.OnBoardingItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingView : Fragment() {

    private var _binding: FragmentOnboadingViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OnBoardingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboadingViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    private fun initFragment(){
        val onBoardingAdapter = initAdapter()
        binding.onBoardingViewPager.adapter = onBoardingAdapter

        setOnBoardingIndicators(onBoardingAdapter.itemCount)
        setCurrentOnBoardingIndicators(0,onBoardingAdapter.itemCount)
        binding.onBoardingViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnBoardingIndicators(position, onBoardingAdapter.itemCount)
            }
        })

        binding.buttonOnBoardingAction.setOnClickListener {
            if (binding.onBoardingViewPager.currentItem + 1 < onBoardingAdapter.itemCount) {
                binding.onBoardingViewPager.currentItem += 1
            } else {
                viewModel.setOnBoardingCompleted()
                findNavController().navigate(R.id.action_onBoardingView_to_homeView)
            }



        }

    }

    private fun initAdapter() : OnBoardingAdapter {
        return OnBoardingAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.animator_window_on_boarding,
                    getString(R.string.on_boarding_title_1),
                    getString(R.string.on_boarding_description_1)
                ),
                OnBoardingItem(
                    R.drawable.animator_lost_online,
                    getString(R.string.on_boarding_title_2),
                    getString(R.string.on_boarding_description_2)
                ),
                OnBoardingItem(
                    R.drawable.animator_download_on_boarding,
                    getString(R.string.on_boarding_title_3),
                    getString(R.string.on_boarding_description_3)
                )
            )
        )
    }

    private fun setOnBoardingIndicators(numberOfPages: Int) {
        val layoutParams =  LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i : Int in 0 until numberOfPages) {
            val indicator =  ImageView(context)
            indicator.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(), R.drawable.onboarding_indicator_inactive
                )
            )
            indicator.layoutParams = layoutParams
            binding.layoutOnBoardingIndicators.addView(indicator)
        }
    }

    private fun setCurrentOnBoardingIndicators(index: Int, itemsTotal: Int) {
        val childCount = binding.layoutOnBoardingIndicators.childCount
        for (i :Int in 0 until childCount) {
            val imageView : ImageView = binding.layoutOnBoardingIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.onboarding_indicator_inactive
                    )
                )
            }
        }
        if (index == itemsTotal - 1){
            binding.buttonOnBoardingAction.text = getString(R.string.on_boarding_get_started)
        }else {
            binding.buttonOnBoardingAction.text = getString(R.string.on_boarding_next)
        }

        val drawableOfPosition = binding.onBoardingViewPager.findViewById<ImageView>(R.id.imageOnBoarding)?.drawable
        if(drawableOfPosition is AnimatedVectorDrawable) {
            drawableOfPosition.reset()
            drawableOfPosition.start()
        }
    }

}