package ugm.bootcamp.teti.todo.ui.todo_create_update

import ugm.bootcamp.teti.todo.data.model.Todo

sealed class TodoCreateUpdateEvent {
    data class OnLoad(val todo: Todo) : TodoCreateUpdateEvent()
    data class OnTitleChange(val title: String) : TodoCreateUpdateEvent()
    data class OnDescriptionChange(val description: String) : TodoCreateUpdateEvent()
    object OnCreateOrUpdateClick : TodoCreateUpdateEvent()
}