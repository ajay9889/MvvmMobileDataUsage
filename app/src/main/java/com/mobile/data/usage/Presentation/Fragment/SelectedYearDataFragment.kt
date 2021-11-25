package com.mobile.data.usage.Presentation.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.data.usage.Adapter.CardListAdapter
import com.mobile.data.usage.Core.apputils.GridSpacingItemDecoration
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import com.mobile.data.usage.Presentation.ViewModel.YearViewModel
import com.mobile.data.usage.R
import com.mobile.data.usage.databinding.FragmentSecondBinding
import com.mobile.data.usage.databinding.SelectYearItemBinding
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SelectedYearDataFragment : BaseFragment<SelectYearItemBinding>(SelectYearItemBinding::inflate) {
    private val mYearViewModel: YearViewModel by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inInformations()
    }
    fun inInformations(){
        val  mMobileDataDomain =arguments?.getSerializable(SecondFragment.ARG_DATA) as MobileDataDomain
        with(viewBinding){
            recyclerView.layoutManager =LinearLayoutManager(requireContext())
            val itemRecords = CardListAdapter(requireContext(),"1", null)
            recyclerView.adapter=itemRecords
            mYearViewModel.getSelectedYearRecords(mMobileDataDomain.year!!).subscribe(
                Consumer { pagingdata ->
                    itemRecords.submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
        }
    }
//    fun showMinimumUseInThEQuerter(mRecord: MobileDataDomain){
//
//    }
    companion object {
        private const val ARG_DATA = "selected_data"
        @JvmStatic
        fun newInstance(mMobileDataDomain: MobileDataDomain): SelectedYearDataFragment {
            return SelectedYearDataFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATA, mMobileDataDomain)
                }
            }
        }
    }
}