package ugm.bootcamp.teti.todo.di

import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.data.repository.TodoRepository
import ugm.bootcamp.teti.todo.util.EmailValidator
import ugm.bootcamp.teti.todo.util.PasswordConfirmValidator
import ugm.bootcamp.teti.todo.util.PasswordValidator

object AppModule {

    private val authRepository = AuthRepository.Impl()
    private val todoRepository = TodoRepository.Impl()

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()
    private val passwordConfirmValidator = PasswordConfirmValidator()

    fun provideAuthRepository(): AuthRepository {
        return authRepository
    }

    fun provideTodoRepository(): TodoRepository {
        return todoRepository
    }

    fun provideEmailValidator(): EmailValidator {
        return emailValidator
    }

    fun providePasswordValidator(): PasswordValidator {
        return passwordValidator
    }

    fun providePasswordConfirmValidator(): PasswordConfirmValidator {
        return passwordConfirmValidator
    }
}