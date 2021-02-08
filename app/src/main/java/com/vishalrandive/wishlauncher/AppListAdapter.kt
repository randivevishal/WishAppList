package com.vishalrandive.wishlauncher

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.vishalrandive.wishlauncher.databinding.ListItemBinding
import com.vishalrandive.wishlib.AppInfoModel

class AppListAdapter(var appInfoModelList: ArrayList<AppInfoModel>) :
    RecyclerView.Adapter<AppListAdapter.ListItemViewHolder>(), Filterable {

    var appFilterList = ArrayList<AppInfoModel>()

    init {
        appFilterList = appInfoModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val listItemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(listItemBinding)
    }

    fun changeData(appInfoListModel: ArrayList<AppInfoModel>) {
        this.appFilterList = appInfoListModel
        notifyDataSetChanged()
    }

    fun clear() {
        this.appFilterList.clear()
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = appFilterList.size

    fun getItem(position: Int): AppInfoModel = appFilterList[position];

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    appFilterList = appInfoModelList
                } else {
                    val resultList = ArrayList<AppInfoModel>()
                    for (row in appInfoModelList) {
                        if (row.appName.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    appFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = appFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                appFilterList = results?.values as ArrayList<AppInfoModel>
                notifyDataSetChanged()

            }

        }
    }
}