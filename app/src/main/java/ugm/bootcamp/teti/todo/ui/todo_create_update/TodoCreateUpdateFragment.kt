package ugm.bootcamp.teti.todo.ui.todo_create_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ugm.bootcamp.teti.todo.R
import ugm.bootcamp.teti.todo.databinding.FragmentTodoCreateUpdateBinding

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
        viewModel = ViewModelProvider(this).get(TodoCreateUpdateViewModel::class.java)
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
        }

    }

}