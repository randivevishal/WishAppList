package com.vishalrandive.wishlauncher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vishalrandive.wishlauncher.databinding.ListItemBinding
import com.vishalrandive.wishlib.AppInfoModel

class AppListAdapter(var appInfoListModel: ArrayList<AppInfoModel>) :
    RecyclerView.Adapter<AppListAdapter.ListItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        // Create a new view, which defines the UI of the list item
        val listItemBinding =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(listItemBinding)
    }

    fun changeData(appInfoListModel: ArrayList<AppInfoModel>) {
        this.appInfoListModel = appInfoListModel
    }

    fun clear() {
        this.appInfoListModel.clear()
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = appInfoListModel.size

    fun getItem(position: Int): AppInfoModel = appInfoListModel[position];

    inner class ListItemViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.setVariable(BR.dataModel, getItem(adapterPosition))
                binding.executePendingBindings()
                binding.root.setOnClickListener {
                    getItem(position)?.let { it1 ->
                        onItemClickListener?.invoke(it1)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((AppInfoModel) -> Unit)? = null

    fun onItemClicked(listener: (AppInfoModel) -> Unit) {
        this.onItemClickListener = listener
    }

}