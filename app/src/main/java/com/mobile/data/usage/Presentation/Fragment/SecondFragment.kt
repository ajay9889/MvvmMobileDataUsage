package com.mobile.data.usage.Presentation.Fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.mobile.data.usage.Adapter.YearDataPagerAdapter
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.ViewModel.YearViewModel
import com.mobile.data.usage.databinding.FragmentSecondBinding
import org.koin.android.ext.android.inject

/**
 * Created by Ajay to manage the view pager to show each year mobile data usage
 */
class SecondFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {
    private val mYearViewModel: YearViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inItDataRenderInViewPager()

    }

    fun inItDataRenderInViewPager(){
       val  mMobileDataDomain =arguments?.getSerializable(ARG_DATA) as MobileDataDomain
        Snackbar.make(viewBinding.root, "User is viewing ${mMobileDataDomain.year}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

        val itemList =mYearViewModel.getSelectedAllYearData();
        val pos =mYearViewModel.getSelectedAllYearData().indexOf(mMobileDataDomain)

        val sectionsPagerAdapter = mMobileDataDomain.year?.let { itemList }?.let {
                YearDataPagerAdapter(requireContext(),childFragmentManager,
                    it
                )
            }
        viewBinding.viewPager.adapter = sectionsPagerAdapter
        viewBinding.viewPager.offscreenPageLimit=itemList.size
        viewBinding.viewPager.setCurrentItem(if(pos>0 ) pos else 0, true)
        viewBinding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {
                Snackbar.make(viewBinding.root, "User is viewing ${itemList.get(position).year}", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
    companion object{
         const val ARG_DATA = "selected_data"
    }
}