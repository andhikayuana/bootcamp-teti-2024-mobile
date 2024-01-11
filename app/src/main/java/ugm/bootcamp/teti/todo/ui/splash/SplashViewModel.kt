package ugm.bootcamp.teti.todo.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.util.UiEffect

class SplashViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnGotoLogin -> uiEffect.postValue(UiEffect.Navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment()))
            SplashEvent.OnGotoTodoList -> uiEffect.postValue(
                UiEffect.Navigate(
                    SplashFragmentDirections.actionSplashFragmentToTodoListFragment()
                )
            )

            SplashEvent.OnLoad -> {
                viewModelScope.launch {
                    delay(1000)
                    if (authRepository.isAuthenticated()) {
                        onEvent(SplashEvent.OnGotoTodoList)
                    } else {
                        onEvent(SplashEvent.OnGotoLogin)
                    }
                }
            }
        }
    }
}