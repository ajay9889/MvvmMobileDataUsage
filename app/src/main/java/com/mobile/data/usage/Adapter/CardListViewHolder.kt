package com.mobile.data.usage.Adapter
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import com.mobile.data.usage.Core.base.BaseViewHolder
import com.mobile.data.usage.DataSource.model.Record
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.databinding.CardviewRowBinding

class CardListViewHolder (viewGroup: ViewGroup , val showMinimumUseInThEQuerter:((MobileDataDomain)->Unit)?=null) : BaseViewHolder<CardviewRowBinding>(viewGroup ,CardviewRowBinding::inflate) {
    fun bindView(mRecord: MobileDataDomain){
        with(viewBinding){
           textView.setText(mRecord.volume_of_mobile_data)
           year.setText(mRecord.year)
            viewBinding.imageItemClicks.setOnClickListener {
                showMinimumUseInThEQuerter?.invoke(mRecord)
            }
        }
    }

}