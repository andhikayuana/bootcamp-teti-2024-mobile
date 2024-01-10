package ugm.bootcamp.teti.todo.ui.todo_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ugm.bootcamp.teti.todo.data.repository.AuthRepository
import ugm.bootcamp.teti.todo.data.repository.TodoRepository
import ugm.bootcamp.teti.todo.ui.profile.ProfileActivity
import ugm.bootcamp.teti.todo.util.UiEffect

class TodoListViewModel(
    private val authRepository: AuthRepository,
    private val todoRepository: TodoRepository,
) : ViewModel() {

    val todoListState: MutableLiveData<TodoListState> by lazy {
        MutableLiveData<TodoListState>(TodoListState())
    }
    val uiEffect: MutableLiveData<UiEffect> by lazy {
        MutableLiveData<UiEffect>()
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            TodoListEvent.OnTodoAddClick -> uiEffect.postValue(
                UiEffect.Navigate(
                    TodoListFragmentDirections.actionTodoListFragmentToTodoCreateUpdateFragment()
                )
            )

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    try {
                        todoRepository.createOrUpdate(event.todo)
                        onEvent(TodoListEvent.OnTodoFetch)
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }
                }
            }
            TodoListEvent.OnLogoutClick -> {
                viewModelScope.launch {
                    try {
                        authRepository.logout()
                        uiEffect.postValue(
                            UiEffect.Navigate(TodoListFragmentDirections.actionTodoListFragmentToLoginFragment())
                        )
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )

                    }
                }

            }

            TodoListEvent.OnProfileClick -> uiEffect.postValue(
                UiEffect.NavigateActivity(ProfileActivity::class.java.name)
            )
            is TodoListEvent.OnTodoClick -> uiEffect.postValue(
                UiEffect.Navigate(
                    TodoListFragmentDirections.actionTodoListFragmentToTodoCreateUpdateFragment(
                        event.todo
                    )
                )
            )

            is TodoListEvent.OnTodoLongClick -> {
                viewModelScope.launch {
                    try {
                        todoRepository.delete(event.todo.id)
                        onEvent(TodoListEvent.OnTodoFetch)
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }
                }
            }

            TodoListEvent.OnTodoFetch -> {
                viewModelScope.launch {
                    try {
                        val todos = todoRepository.all()
                        todoListState.postValue(todoListState.value?.copy(todos = todos))
                    } catch (e: Exception) {
                        uiEffect.postValue(
                            UiEffect.ShowToast(
                                e.message ?: "Oops, something went wrong!"
                            )
                        )
                    }
                }
            }
        }
    }
}