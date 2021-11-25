package com.mobile.data.usage.Adapter
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import com.mobile.data.usage.Core.base.BaseViewHolder
import com.mobile.data.usage.DataSource.model.Record
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.databinding.CardviewRowBinding
import com.mobile.data.usage.databinding.QuraterItemBinding

class YearsListViewHolder (viewGroup: ViewGroup, val showMinimumUseInThEQuerter:((MobileDataDomain)->Unit)?=null) : BaseViewHolder<QuraterItemBinding>(viewGroup ,QuraterItemBinding::inflate) {
    fun bindView(mRecord: MobileDataDomain){
        with(viewBinding){
           consumtion.setText(mRecord.volume_of_mobile_data)
           year.setText(mRecord.year)
            quarter.setText(mRecord.quarter)
        }
    }

}