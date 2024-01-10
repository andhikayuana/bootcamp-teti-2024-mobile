package ugm.bootcamp.teti.todo.ui.todo_list

import ugm.bootcamp.teti.todo.data.model.Todo

sealed class TodoListEvent {
    object OnLogoutClick : TodoListEvent()
    object OnProfileClick : TodoListEvent()
    object OnTodoAddClick : TodoListEvent()
    object OnTodoFetch : TodoListEvent()

    data class OnDoneChange(val todo: Todo) : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    data class OnTodoLongClick(val todo: Todo) : TodoListEvent()
}