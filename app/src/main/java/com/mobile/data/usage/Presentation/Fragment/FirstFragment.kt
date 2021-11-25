package com.mobile.data.usage.Presentation.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.data.usage.Adapter.CardListAdapter
import com.mobile.data.usage.Core.apputils.DsAlert
import com.mobile.data.usage.Core.apputils.GridSpacingItemDecoration
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import com.mobile.data.usage.R
import com.mobile.data.usage.databinding.YearlyGridDataBinding
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment<YearlyGridDataBinding>(YearlyGridDataBinding::inflate) {
    private val mHomeViewModel: HomeViewModel by inject()
    lateinit var mAlertDialog : ProgressBar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAlertDialog =  DsAlert.getProgressDialogue(requireContext())
        mAlertDialog.visibility=View.VISIBLE
        observeData()
    }
    fun observeData(){
        mHomeViewModel.mRequestState.observe(viewLifecycleOwner, {
            when(it)
            {
                HomeViewModel.RequestState.network_error->{

                    DsAlert.show(requireActivity() ,
                        requireContext().getString(R.string.warning),
                        requireContext().getString(R.string.net_error),
                        requireContext().getString(R.string.ok_btn)
                    );
                }
                HomeViewModel.RequestState.error->{
                    DsAlert.show(requireActivity() ,
                        requireContext().getString(R.string.warning),
                        requireContext().getString(R.string.normal_error),
                        requireContext().getString(R.string.ok_btn)
                    );
                }
                HomeViewModel.RequestState.finished->{
                    initRecyclerView()
                }
            }
            mAlertDialog.visibility=View.GONE

        })
    }

    fun showMinimumUseInThEQuerter(mRecord: MobileDataDomain){
        val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment, Bundle().apply {
            putSerializable(SecondFragment.ARG_DATA,mRecord);
        })
    }
    @SuppressLint("CheckResult")
    fun initRecyclerView(){
        viewBinding.recyclerView.setHasFixedSize(true)
        val spanCount = 5 // 3 columns

        val spacing = 5 // 50px

        val includeEdge = false
        val widths = resources.displayMetrics.widthPixels.toFloat()
        val heightPixels = resources.displayMetrics.heightPixels.toFloat()
        val width = widths.toInt()
        var gridLayoutManager: GridLayoutManager? = null

        gridLayoutManager = if (widths > heightPixels) {
            GridLayoutManager(requireContext(), 5)
        } else {
            if (width < 700) GridLayoutManager(
                requireContext(),
                2
            ) else GridLayoutManager(requireContext(), 3)
        }
        viewBinding.recyclerView.setLayoutManager(gridLayoutManager)
        val itemDecoration = GridSpacingItemDecoration(spanCount, spacing, includeEdge)
        viewBinding.recyclerView.addItemDecoration(itemDecoration);
        viewBinding.recyclerView.setOnFlingListener(null);
        val itemRecords = CardListAdapter(requireContext(),"0", this::showMinimumUseInThEQuerter)
        viewBinding.recyclerView.adapter=itemRecords
        mHomeViewModel.getDataUsageRecords().subscribe(
                Consumer { pagingdata ->
                    itemRecords.submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
    }
}