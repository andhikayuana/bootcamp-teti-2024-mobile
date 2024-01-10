package ugm.bootcamp.teti.todo.data.model

import java.io.Serializable
import kotlin.random.Random

data class Todo(
    val id: String = "${Random.nextInt()}",
    val title: String = "",
    val description: String? = null,
    val isDone: Boolean = false
) : Serializable