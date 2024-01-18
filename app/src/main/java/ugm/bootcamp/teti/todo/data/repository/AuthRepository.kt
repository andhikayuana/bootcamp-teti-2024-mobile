package ugm.bootcamp.teti.todo.data.repository

import com.google.firebase.auth.FirebaseAuth
import ugm.bootcamp.teti.todo.data.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface AuthRepository {

    suspend fun isAuthenticated(): Boolean

    suspend fun getAuthenticatedUser(): User

    suspend fun register(
        email: String,
        password: String
    ): Boolean

    suspend fun login(
        email: String,
        password: String
    ): Boolean

    suspend fun logout()

    class Impl(
        private val firebaseAuth: FirebaseAuth
    ) : AuthRepository {

        override suspend fun isAuthenticated(): Boolean {
            return firebaseAuth.currentUser != null
        }

        override suspend fun getAuthenticatedUser(): User {
            return firebaseAuth.currentUser?.email?.let { email ->
                User(email)
            } ?: User()
        }

        override suspend fun register(email: String, password: String): Boolean {
            return suspendCoroutine { continuation ->
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        continuation.resume(it.user != null)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun login(email: String, password: String): Boolean {
            return suspendCoroutine { continuation ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        continuation.resume(it.user != null)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun logout() {
            firebaseAuth.signOut()
        }

    }
}