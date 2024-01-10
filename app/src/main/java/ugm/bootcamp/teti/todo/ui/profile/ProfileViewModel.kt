package ugm.bootcamp.teti.todo.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.model.User
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.util.UiEffect

class ProfileViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    val profileState: MutableLiveData<User> by lazy {
        MutableLiveData<User>(User())
    }
    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnLoadProfile -> {
                viewModelScope.launch {
                    try {
                        authRepository.getAuthenticatedUser().also {
                            profileState.postValue(it)
                        }
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }
                }
            }
        }
    }
}