package ugm.bootcamp.teti.todo.ui.todo_create_update

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.model.Todo
import ugm.bootcamp.teti.todo.data.repository.TodoRepository
import ugm.bootcamp.teti.todo.util.UiEffect

class TodoCreateUpdateViewModel(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todoCreateUpdateState: MutableLiveData<Todo> by lazy {
        MutableLiveData<Todo>(Todo())
    }
    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: TodoCreateUpdateEvent) {
        when (event) {
            TodoCreateUpdateEvent.OnCreateOrUpdateClick -> {
                try {
                    viewModelScope.launch {
                        todoCreateUpdateState.value?.let {
                            todoRepository.createOrUpdate(it)
                        }
                    }
                    uiEffect.postValue(UiEffect.PopBackStack)
                } catch (e: Exception) {
                    uiEffect.postValue(
                        UiEffect.ShowToast(
                            e.message ?: "Oops, something went wrong!"
                        )
                    )
                }
            }

            is TodoCreateUpdateEvent.OnDescriptionChange -> {
                todoCreateUpdateState.postValue(todoCreateUpdateState.value?.copy(description = event.description))
            }

            is TodoCreateUpdateEvent.OnTitleChange -> todoCreateUpdateState.postValue(
                todoCreateUpdateState.value?.copy(title = event.title)
            )

            is TodoCreateUpdateEvent.OnLoad -> todoCreateUpdateState.postValue(
                event.todo
            )
        }
    }
}