package montanez.alexander.saturnwallpapers.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository

class OnBoardingViewModel(
    private val preferencesRepository: IPreferencesRepository
) : ViewModel() {

    fun setOnBoardingCompleted(){
        viewModelScope.launch {
            preferencesRepository.setOnBoardingComplete(true)
            preferencesRepository.initPreferences()
        }
    }


}