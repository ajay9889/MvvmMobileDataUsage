package com.mobile.data.usage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.data.usage.Adapter.CardListAdapter
import com.mobile.data.usage.Core.base.BaseFragment
import com.mobile.data.usage.Presentation.ViewModel.HomeViewModel
import com.mobile.data.usage.databinding.FragmentFirstBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import org.koin.java.KoinJavaComponent
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.data.usage.Core.apputils.GridSpacingItemDecoration


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment<FragmentFirstBinding>(FragmentFirstBinding::inflate) {
    var mHomeViewModel: Lazy<HomeViewModel> =  KoinJavaComponent.inject<HomeViewModel>(HomeViewModel::class.java)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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
        val itemRecords = CardListAdapter(requireContext())
        viewBinding.recyclerView.adapter=itemRecords
        mHomeViewModel.value.getDataUsageRecords()
            .observeOn(AndroidSchedulers.mainThread()).subscribe(
                Consumer { pagingdata ->
                    itemRecords.submitData(
                        lifecycle,
                        pagingdata
                    )
                }
            )
    }
}