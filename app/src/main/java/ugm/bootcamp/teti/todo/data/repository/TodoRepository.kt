package ugm.bootcamp.teti.todo.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import ugm.bootcamp.teti.todo.data.model.Todo
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface TodoRepository {

    companion object {
        private const val COLLECTION_APP = "todoapp"
        private const val COLLECTION_NAME = "todos"
    }

    suspend fun createOrUpdate(todo: Todo)

    suspend fun get(id: String): Todo?

    suspend fun delete(id: String)

    suspend fun all(): List<Todo>

    class Impl(
        private val authRepository: AuthRepository,
        private val firebaseFirestore: FirebaseFirestore
    ) : TodoRepository {

        private val todos: MutableMap<String, MutableMap<String, Todo>> = mutableMapOf()

        override suspend fun createOrUpdate(todo: Todo) {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .document(todo.id)
                    .set(todo)
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun get(id: String): Todo? {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            return suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .document(id)
                    .get()
                    .addOnSuccessListener {
                        continuation.resume(it.toObject<Todo>())
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun delete(id: String) {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .document(id)
                    .delete()
                    .addOnSuccessListener {
                        continuation.resume(Unit)
                    }
                    .addOnFailureListener {
                        continuation.resumeWithException(it)
                    }
            }
        }

        override suspend fun all(): List<Todo> {
            val currentUserEmail = authRepository.getAuthenticatedUser().email
            return suspendCoroutine { continuation ->
                firebaseFirestore.collection(COLLECTION_APP)
                    .document(currentUserEmail)
                    .collection(COLLECTION_NAME)
                    .get()
                    .addOnSuccessListener {
                        continuation.resume(it.documents.map { it.toObject<Todo>() ?: Todo() }
                            .toList())
                    }
                    .addOnFailureListener {
                        continuation.resume(emptyList())
                    }
            }
        }

    }

}