package montanez.alexander.saturnwallpapers.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import montanez.alexander.saturnwallpapers.repository.IPreferencesRepository

class SplashViewModel(
    private val preferencesRepository: IPreferencesRepository
) : ViewModel() {

    val wasOnBoardingCompleted = MutableLiveData<Boolean>()

    fun checkOnBoardingStatus(){
        viewModelScope.launch {
            preferencesRepository.wasOnBoardingCompleted().collect{
                wasOnBoardingCompleted.value = it
            }
        }
    }

}