package ugm.bootcamp.teti.todo.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ugm.bootcamp.teti.todo.data.remote.ApiService
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.data.repository.BannerRepository
import ugm.bootcamp.teti.todo.data.repository.TodoRepository
import ugm.bootcamp.teti.todo.util.EmailValidator
import ugm.bootcamp.teti.todo.util.PasswordConfirmValidator
import ugm.bootcamp.teti.todo.util.PasswordValidator

object AppModule {

    private val firebaseAuth: FirebaseAuth = Firebase.auth
    private val firebaseFirestore: FirebaseFirestore = Firebase.firestore

    private const val baseUrl = "https://bootcamp-teti-ugm-2024.web.app"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    private val authRepository = AuthRepository.Impl(firebaseAuth)
    private val todoRepository = TodoRepository.Impl(authRepository, firebaseFirestore)
    private val bannerRepository = BannerRepository.Impl(apiService)

    private val emailValidator = EmailValidator()
    private val passwordValidator = PasswordValidator()
    private val passwordConfirmValidator = PasswordConfirmValidator()

    fun provideBannerRepository(): BannerRepository {
        return bannerRepository
    }

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