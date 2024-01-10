package ugm.bootcamp.teti.todo.ui.todo_list

import ugm.bootcamp.teti.todo.data.model.Todo

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val imageUrls: List<String> = emptyList()
)