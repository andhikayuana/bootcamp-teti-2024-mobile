package ugm.bootcamp.teti.todo.data.repository

import ugm.bootcamp.teti.todo.data.model.Todo

interface TodoRepository {

    suspend fun createOrUpdate(todo: Todo)

    suspend fun get(id: String): Todo?

    suspend fun delete(id: String)

    suspend fun all(): List<Todo>

    class Impl : TodoRepository {

        private val todos: MutableMap<String, Todo> = mutableMapOf()
        override suspend fun createOrUpdate(todo: Todo) {
            todos[todo.id] = todo
        }

        override suspend fun get(id: String): Todo? {
            return todos[id]
        }

        override suspend fun delete(id: String) {
            todos.remove(id)
        }

        override suspend fun all(): List<Todo> {
            return todos.values.toList()
        }

    }

}