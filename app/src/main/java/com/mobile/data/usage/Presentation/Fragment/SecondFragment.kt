package com.mobile.data.usage.Presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.mobile.data.usage.Adapter.YearDataPagerAdapter
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.ViewModel.YearViewModel
import com.mobile.data.usage.databinding.FragmentSecondBinding
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SecondFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {
    private val mYearViewModel: YearViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inItDataRenderInViewPager()
    }
    fun inItDataRenderInViewPager(){
       val  mMobileDataDomain =arguments?.getSerializable(ARG_DATA) as MobileDataDomain
        val itemList =mYearViewModel.getSelectedAllYearData();
        val pos =mYearViewModel.getSelectedAllYearData().indexOf(mMobileDataDomain)
        Log.d("TAGS", "${mMobileDataDomain}")
        val sectionsPagerAdapter = mMobileDataDomain.year?.let { itemList }?.let {
                YearDataPagerAdapter(requireContext(),childFragmentManager,
                    it
                )
            }
        viewBinding.viewPager.adapter = sectionsPagerAdapter
        viewBinding.viewPager.setCurrentItem(if(pos>0 ) pos else 0, true)
    }

    companion object{
         const val ARG_DATA = "selected_data"
    }
}