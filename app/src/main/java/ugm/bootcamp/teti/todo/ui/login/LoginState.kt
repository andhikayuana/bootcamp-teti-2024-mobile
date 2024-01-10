package ugm.bootcamp.teti.todo.ui.login

data class LoginState(
    val email: String = "",
    val emailErrorMessage: String? = null,
    val password: String = "",
    val passwordErrorMessage: String? = null
) {

    fun hasEmailError(): Boolean = emailErrorMessage != null

    fun hasPasswordError(): Boolean = passwordErrorMessage != null

    fun resetErrorMessages(): LoginState =
        copy(emailErrorMessage = null, passwordErrorMessage = null)
}