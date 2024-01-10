package ugm.bootcamp.teti.todo.ui.todo_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ugm.bootcamp.teti.todo.data.model.Todo
import ugm.bootcamp.teti.todo.databinding.FragmentTodoListBinding

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

    }

    private fun onTodoLongClick(todo: Todo) {

    }

    private fun onTodoClick(todo: Todo) {
        findNavController().navigate(
            TodoListFragmentDirections.actionTodoListFragmentToTodoCreateUpdateFragment(
                todo
            )
        )
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
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvTodoList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionTodoListFragmentToTodoCreateUpdateFragment())
        }

        (1..10).map {
            Todo(
                "$id",
                "title $it",
                "description lorem ipsum dolor sit amet $it\n new line here\nnew line again here"
            )
        }.let {
            todoAdapter.submitList(it)
        }

    }

}