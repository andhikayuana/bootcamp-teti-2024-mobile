package ugm.bootcamp.teti.todo.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.util.EmailValidator
import ugm.bootcamp.teti.todo.util.PasswordValidator
import ugm.bootcamp.teti.todo.util.UiEffect

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) : ViewModel() {

    val loginState: MutableLiveData<LoginState> by lazy {
        MutableLiveData<LoginState>(LoginState())
    }
    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                loginState.postValue(loginState.value?.copy(email = event.email))
            }

            LoginEvent.OnGotoRegisterClick -> {
                uiEffect.postValue(UiEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment()))
            }

            LoginEvent.OnLoginClick -> {
                loginState.postValue(loginState.value?.resetErrorMessages())

                val emailResult = emailValidator.execute(loginState.value?.email.orEmpty())
                val passwordResult = passwordValidator.execute(loginState.value?.password.orEmpty())

                val hasError = listOf(emailResult, passwordResult).any { it.successful.not() }

                if (hasError) {
                    loginState.postValue(
                        loginState.value?.copy(
                            emailErrorMessage = emailResult.errorMessage,
                            passwordErrorMessage = passwordResult.errorMessage
                        )
                    )
                    return
                }

                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val result = authRepository.login(
                            email = loginState.value?.email.orEmpty(),
                            password = loginState.value?.password.orEmpty()
                        )

                        if (result) {
                            uiEffect.postValue(UiEffect.Navigate(LoginFragmentDirections.actionLoginFragmentToTodoListFragment()))
                        } else {
                            throw Exception("Your email or password didn't match!")
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

            is LoginEvent.OnPasswordChange -> {
                loginState.postValue(loginState.value?.copy(password = event.password))
            }
        }
    }

}