package com.mobile.data.usage.Presentation.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.data.usage.Adapter.CardListAdapter
import com.mobile.data.usage.Core.apputils.DsAlert
import com.mobile.data.usage.Core.apputils.GridSpacingItemDecoration
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Core.networkutils.NetworkConnectivity
import com.mobile.data.usage.Domain.Model.MobileDataDomain
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import com.mobile.data.usage.R
import com.mobile.data.usage.databinding.YearlyGridDataBinding
import io.reactivex.functions.Consumer
import org.koin.android.ext.android.inject


/**
 * Created by Ajay to show the commulative data usage per year. Also user can filter the year
 */
class FirstFragment : BaseFragment<YearlyGridDataBinding>(YearlyGridDataBinding::inflate) {
    private val mHomeViewModel: HomeViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.progressBar.visibility=View.VISIBLE
        initRecyclerViewDecorate()
        observeData()
    }
    fun observeData(){
        mHomeViewModel.mRequestState.observe(viewLifecycleOwner, {
            when(it)
            {
                HomeViewModel.RequestState.network_error->{
                    DsAlert.showAlertFinish(requireActivity() ,
                        requireContext().getString(R.string.warning),
                        requireContext().getString(R.string.net_error),
                        requireContext().getString(R.string.ok_btn));
                    viewBinding.progressBar.visibility=View.GONE
                }
                HomeViewModel.RequestState.error->{
                    DsAlert.showAlert(requireActivity() ,
                        requireContext().getString(R.string.warning),
                        requireContext().getString(R.string.normal_error),
                        requireContext().getString(R.string.ok_btn)
                    );
                    viewBinding.progressBar.visibility=View.GONE
                }
                HomeViewModel.RequestState.finished->{
                    initRecyclerView("")
                    viewBinding.progressBar.visibility=View.GONE
                }
            }
        })
        mHomeViewModel.mRequestState.value =(HomeViewModel.RequestState.finished)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        menu.add(1,1, Menu.NONE, "All");
        for(i in 2007 until 2022)
        {
            menu.add(1, i, Menu.NONE, i.toString());
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            1 ->{
                initRecyclerView("")
            }
            else ->{
                initRecyclerView(item.itemId.toString())

            }
        }
        return   super.onOptionsItemSelected(item)
    }
    fun showMinimumUseInThEQuerter(mRecord: MobileDataDomain){
        val navController = requireActivity().findNavController(R.id.nav_host_fragment_content_main)
        navController.navigate(R.id.action_FirstFragment_to_SecondFragment, Bundle().apply {
            putSerializable(SecondFragment.ARG_DATA,mRecord);
        })
    }
    @SuppressLint("CheckResult")
    fun initRecyclerViewDecorate(){
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

        val itemRecordsAdapter = CardListAdapter(requireContext(),"0", this::showMinimumUseInThEQuerter)
        viewBinding.recyclerView.adapter=itemRecordsAdapter

        itemRecordsAdapter.addLoadStateListener { loadState ->
            if(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && itemRecordsAdapter.itemCount<1 )
            {
                mHomeViewModel.mRequestState.value =(HomeViewModel.RequestState.error)
            }else if (loadState.source.refresh is LoadState.Error) {
                mHomeViewModel.mRequestState.value =(HomeViewModel.RequestState.network_error)
            }
        }
    }
    @SuppressLint("CheckResult")
    fun initRecyclerView(selectedYear: String){
        mHomeViewModel.getDataUsageRecords(selectedYear).subscribe(
            Consumer { pagingdata ->
                (viewBinding.recyclerView.adapter as CardListAdapter).submitData(
                    lifecycle,
                    pagingdata
                )
            }
        )
    }
}