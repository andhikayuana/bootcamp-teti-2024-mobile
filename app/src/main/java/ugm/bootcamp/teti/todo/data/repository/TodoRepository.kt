package ugm.bootcamp.teti.todo.data.repository

import ugm.bootcamp.teti.todo.data.model.Todo

interface TodoRepository {

    suspend fun createOrUpdate(todo: Todo)

    suspend fun get(id: String): Todo?

    suspend fun delete(id: String)

    suspend fun all(): List<Todo>

    class Impl(
        private val authRepository: AuthRepository
    ) : TodoRepository {

        private val todos: MutableMap<String, MutableMap<String, Todo>> = mutableMapOf()
        override suspend fun createOrUpdate(todo: Todo) {
            if (todos.containsKey(authRepository.getAuthenticatedUser().email)) {
                todos[authRepository.getAuthenticatedUser().email]?.set(todo.id, todo)
            } else {
                todos[authRepository.getAuthenticatedUser().email] = mutableMapOf(todo.id to todo)
            }
        }

        override suspend fun get(id: String): Todo? {
            return todos[authRepository.getAuthenticatedUser().email]?.get(id)
        }

        override suspend fun delete(id: String) {
            todos[authRepository.getAuthenticatedUser().email]?.remove(id)
        }

        override suspend fun all(): List<Todo> {
            return todos[authRepository.getAuthenticatedUser().email]?.values?.toList()
                ?: emptyList()
        }

    }

}