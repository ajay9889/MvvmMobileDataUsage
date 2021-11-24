package com.mobile.data.usage.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.data.usage.DataSource.model.Record

class CardListAdapter(val context: Context) : PagingDataAdapter<Record, RecyclerView.ViewHolder>(DataDifferentiator) {
    object DataDifferentiator: DiffUtil.ItemCallback<Record>(){
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem._id == newItem._id
        }
        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem._id == newItem._id
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as CardListViewHolder).bindView(it)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardListViewHolder(parent)
    }
}
