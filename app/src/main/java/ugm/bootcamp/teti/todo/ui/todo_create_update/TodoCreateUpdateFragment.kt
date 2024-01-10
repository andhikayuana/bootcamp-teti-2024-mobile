package ugm.bootcamp.teti.todo.ui.todo_create_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ugm.bootcamp.teti.todo.R
import ugm.bootcamp.teti.todo.TodoApp
import ugm.bootcamp.teti.todo.databinding.FragmentTodoCreateUpdateBinding
import ugm.bootcamp.teti.todo.util.UiEffect

class TodoCreateUpdateFragment : Fragment() {

    private lateinit var viewModel: TodoCreateUpdateViewModel
    private var _binding: FragmentTodoCreateUpdateBinding? = null
    private val binding get() = _binding!!
    private val args: TodoCreateUpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoCreateUpdateBinding.inflate(inflater, container, false)
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
        viewModel = TodoCreateUpdateViewModel(
            appModule.provideTodoRepository()
        )
        viewModel.todoCreateUpdateState.observe(this, Observer { state ->

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

                else -> {}
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isCreate = args.todo == null

        binding.apply {
            topAppBar.title = getString(
                if (isCreate) R.string.label_todo_add else R.string.label_todo_update
            )
            btnCreateOrUpdate.text =
                getString(if (isCreate) R.string.label_create else R.string.label_update)

            tieTitle.setText(args.todo?.title.orEmpty())
            tieDescription.setText(args.todo?.description.orEmpty())

            tieTitle.addTextChangedListener {
                viewModel.onEvent(TodoCreateUpdateEvent.OnTitleChange(it.toString()))
            }
            tieDescription.addTextChangedListener {
                viewModel.onEvent(TodoCreateUpdateEvent.OnDescriptionChange(it.toString()))
            }
            btnCreateOrUpdate.setOnClickListener {
                viewModel.onEvent(TodoCreateUpdateEvent.OnCreateOrUpdateClick)
            }
            if (isCreate.not()) {
                args.todo?.let {
                    viewModel.onEvent(TodoCreateUpdateEvent.OnLoad(it))
                }
            }
        }

    }

}