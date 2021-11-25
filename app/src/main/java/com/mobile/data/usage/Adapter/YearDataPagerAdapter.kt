package com.mobile.data.usage.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.Fragment.SelectedYearDataFragment

class YearDataPagerAdapter(private val context: Context, fm: FragmentManager , val queterRecords: List<MobileDataDomain>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return SelectedYearDataFragment.newInstance(queterRecords.get(position))
    }

    override fun getPageTitle(position: Int): CharSequence {
        return "Year"
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return queterRecords.size
    }
}