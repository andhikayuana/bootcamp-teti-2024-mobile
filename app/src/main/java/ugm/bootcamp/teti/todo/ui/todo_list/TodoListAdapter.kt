package ugm.bootcamp.teti.todo.ui.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ugm.bootcamp.teti.todo.data.model.Todo
import ugm.bootcamp.teti.todo.databinding.ItemTodoBinding

typealias OnTodoClick = (todo: Todo) -> Unit
typealias OnTodoLongClick = (todo: Todo) -> Unit
typealias OnTodoDoneChanged = (todo: Todo) -> Unit


class TodoListAdapter(
    private val onTodoClick: OnTodoClick,
    private val onTodoLongClick: OnTodoLongClick,
    private val onTodoDoneChanged: OnTodoDoneChanged,
) : ListAdapter<Todo, TodoListAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(
        private val itemBinding: ItemTodoBinding,
        private val onTodoClick: OnTodoClick,
        private val onTodoLongClick: OnTodoLongClick,
        private val onTodoDoneChanged: OnTodoDoneChanged
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(todo: Todo) = itemBinding.apply {
            tvTitle.text = todo.title
            tvDescription.text = todo.description
            cbDone.isChecked = todo.isDone
            cbDone.setOnCheckedChangeListener { _, value ->
                onTodoDoneChanged(todo.copy(isDone = value))
            }
            llItemTodo.setOnClickListener {
                onTodoClick(todo)
            }
            llItemTodo.setOnLongClickListener {
                onTodoLongClick(todo)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onTodoClick, onTodoLongClick, onTodoDoneChanged)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}