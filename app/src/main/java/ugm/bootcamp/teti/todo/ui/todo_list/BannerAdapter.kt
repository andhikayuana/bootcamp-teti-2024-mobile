package ugm.bootcamp.teti.todo.ui.todo_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ugm.bootcamp.teti.todo.R
import ugm.bootcamp.teti.todo.databinding.ItemBannerBinding

class BannerAdapter : ListAdapter<String, BannerAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(
        private val itemBannerBinding: ItemBannerBinding
    ) : RecyclerView.ViewHolder(itemBannerBinding.root) {
        fun bind(item: String?) = itemBannerBinding.apply {
            sivBanner.load(item) {
                crossfade(true)
                placeholder(R.color.black)
                transformations(RoundedCornersTransformation())
            }
        }

    }
}