package ugm.bootcamp.teti.todo.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.util.EmailValidator
import ugm.bootcamp.teti.todo.util.PasswordConfirmValidator
import ugm.bootcamp.teti.todo.util.PasswordValidator
import ugm.bootcamp.teti.todo.util.UiEffect

class RegisterViewModel(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator,
    private val passwordConfirmValidator: PasswordConfirmValidator
) : ViewModel() {

        val registerState: MutableLiveData<RegisterState> by lazy {
            MutableLiveData<RegisterState>(RegisterState())
        }
        val uiEffect: MutableLiveData<UiEffect> by lazy {
            MutableLiveData<UiEffect>()
        }

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnEmailChange -> {
                registerState.postValue(registerState.value?.copy(email = event.email))
            }

            is RegisterEvent.OnPasswordChange -> {
                registerState.postValue(registerState.value?.copy(password = event.password))
            }

            is RegisterEvent.OnPasswordConfirmChange -> {
                registerState.postValue(registerState.value?.copy(passwordConfirm = event.passwordConfirm))
            }

            RegisterEvent.OnRegisterClick -> {
                registerState.postValue(registerState.value?.resetErrorMessages())

                val emailResult = emailValidator.execute(registerState.value?.email.orEmpty())
                val passwordResult =
                    passwordValidator.execute(registerState.value?.password.orEmpty())
                val passwordConfirmResult =
                    passwordConfirmValidator.execute(
                        password = registerState.value?.password.orEmpty(),
                        passwordConfirm = registerState.value?.passwordConfirm.orEmpty()
                    )

                val hasError = listOf(
                    emailResult,
                    passwordResult,
                    passwordConfirmResult
                ).any { it.successful.not() }

                if (hasError) {
                    registerState.postValue(
                        registerState.value?.copy(
                            emailErrorMessage = emailResult.errorMessage,
                            passwordErrorMessage = passwordResult.errorMessage,
                            passwordConfirmErrorMessage = passwordConfirmResult.errorMessage
                        )
                    )
                    return
                }

                viewModelScope.launch {
                    try {
                        authRepository.register(
                            email = registerState.value?.email.orEmpty(),
                            password = registerState.value?.password.orEmpty()
                        )
                        uiEffect.postValue(UiEffect.PopBackStack)
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