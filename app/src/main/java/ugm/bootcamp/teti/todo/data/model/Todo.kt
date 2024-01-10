package ugm.bootcamp.teti.todo.data.model

import java.io.Serializable

data class Todo(
    val id: String,
    val title: String,
    val description: String? = null,
    val isDone: Boolean = false
) : Serializable