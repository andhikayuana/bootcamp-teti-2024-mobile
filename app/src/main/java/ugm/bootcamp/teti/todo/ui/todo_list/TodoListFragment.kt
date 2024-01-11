package ugm.bootcamp.teti.todo.ui.todo_list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ugm.bootcamp.teti.todo.R
import ugm.bootcamp.teti.todo.TodoApp
import ugm.bootcamp.teti.todo.data.model.Todo
import ugm.bootcamp.teti.todo.databinding.FragmentTodoListBinding
import ugm.bootcamp.teti.todo.util.UiEffect

class TodoListFragment : Fragment() {


    private lateinit var viewModel: TodoListViewModel
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter by lazy {
        TodoListAdapter(
            onTodoClick = ::onTodoClick,
            onTodoLongClick = ::onTodoLongClick,
            onTodoDoneChanged = ::onTodoDoneChanged
        )
    }

    private fun onTodoDoneChanged(todo: Todo) {
        viewModel.onEvent(TodoListEvent.OnDoneChange(todo))
    }

    private fun onTodoLongClick(todo: Todo) {
        viewModel.onEvent(TodoListEvent.OnTodoLongClick(todo))
    }

    private fun onTodoClick(todo: Todo) {
        viewModel.onEvent(TodoListEvent.OnTodoClick(todo))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appModule = (requireActivity().application as TodoApp).appModule
        viewModel = TodoListViewModel(
            appModule.provideAuthRepository(),
            appModule.provideTodoRepository()
        )
        viewModel.todoListState.observe(this, Observer { state ->
            todoAdapter.submitList(state.todos)
        })
        viewModel.uiEffect.observe(this, Observer { effect ->
            when (effect) {
                is UiEffect.Navigate -> findNavController().navigate(effect.directions)
                UiEffect.PopBackStack -> findNavController().popBackStack()
                is UiEffect.ShowToast -> Toast.makeText(
                    requireContext(),
                    effect.message,
                    Toast.LENGTH_SHORT
                ).show()

                is UiEffect.NavigateActivity -> {
                    Intent().apply {
                        setClassName(requireContext(), effect.activityClassName)
                    }.also {
                        startActivity(it)
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionProfile -> viewModel.onEvent(TodoListEvent.OnProfileClick)
                R.id.actionLogout -> viewModel.onEvent(TodoListEvent.OnLogoutClick)
                else -> Unit
            }

            true
        }

        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
        binding.fabAdd.setOnClickListener {
            viewModel.onEvent(TodoListEvent.OnTodoAddClick)
        }
        viewModel.onEvent(TodoListEvent.OnTodoFetch)
    }

}