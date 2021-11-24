package com.mobile.data.usage.Adapter
import android.view.ViewGroup
import com.mobile.data.usage.Core.base.BaseViewHolder
import com.mobile.data.usage.DataSource.model.Record
import com.mobile.data.usage.databinding.CardviewRowBinding

class CardListViewHolder (viewGroup: ViewGroup) : BaseViewHolder<CardviewRowBinding>(viewGroup ,CardviewRowBinding::inflate) {
    fun bindView(mRecord: Record){
        with(viewBinding){
           textView.setText(mRecord.quarter)
            year.setText(mRecord.volume_of_mobile_data)
//            image_item_clicks.setOnClickListener(object : OnClickListener() {
//                fun onClick(v: View?) {
//                    try {
//                        val message =
//                            "The decreases volume of Mobile data " + singleMobileData.getString(com.sun.org.apache.bcel.internal.classfile.Utility.MIN_min_network_data)
//                                .toString() + " quarter " + singleMobileData.getString(com.sun.org.apache.bcel.internal.classfile.Utility.QUARTER)
//                                .toString() + " in a year " + singleMobileData.getString(com.sun.org.apache.bcel.internal.classfile.Utility.YEAR)
//                        showAlert(
//                            singleMobileData.getString(com.sun.org.apache.bcel.internal.classfile.Utility.YEAR)
//                                .toString() + "-" + singleMobileData.getString(com.sun.org.apache.bcel.internal.classfile.Utility.QUARTER),
//                            message
//                        )
//                    } catch (e: Exception) {
//                        // TODO: handle exception
//                        e.printStackTrace()
//                    }
//                }
//            })

        }
    }

}