package com.mobile.data.usage.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobile.data.usage.Domain.Model.MobileDataDomain

class CardListAdapter(val context: Context, val type: String,val showMinimumUseInThEQuerter:((MobileDataDomain)->Unit)?=null) : PagingDataAdapter<MobileDataDomain, RecyclerView.ViewHolder>(DataDifferentiator) {
    object DataDifferentiator: DiffUtil.ItemCallback<MobileDataDomain>(){
        override fun areItemsTheSame(oldItem: MobileDataDomain, newItem: MobileDataDomain): Boolean {
            return oldItem._id == newItem._id
        }
        override fun areContentsTheSame(oldItem: MobileDataDomain, newItem: MobileDataDomain): Boolean {
            return oldItem._id == newItem._id
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {

            when(type)
            {
                "1"->{
                    (holder as YearsListViewHolder).bindView(it )
                }
                else ->{
                    (holder as CardListViewHolder).bindView(it )
                }
            }



        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(type)
        {
            "1"->{
               return YearsListViewHolder(parent,showMinimumUseInThEQuerter)
            }
            else ->{
                return CardListViewHolder(parent,showMinimumUseInThEQuerter)
            }
        }
    }
}
