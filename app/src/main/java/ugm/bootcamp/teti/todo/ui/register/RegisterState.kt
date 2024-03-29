package ugm.bootcamp.teti.todo.ui.register

data class RegisterState(
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null,
    val passwordConfirm: String = "",
    val passwordConfirmErrorMessage: String? = null
) {

    fun hasEmailError(): Boolean = emailErrorMessage != null

    fun hasPasswordError(): Boolean = passwordErrorMessage != null

    fun hasPasswordConfirmError(): Boolean = passwordConfirmErrorMessage != null

    fun resetErrorMessages(): RegisterState = copy(
        emailErrorMessage = null,
        passwordErrorMessage = null,
        passwordConfirmErrorMessage = null
    )
}