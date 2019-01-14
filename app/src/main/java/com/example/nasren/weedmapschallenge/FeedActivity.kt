package com.example.nasren.weedmapschallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nasren.weedmapschallenge.adapters.BusinessAdapter
import com.example.nasren.weedmapschallenge.api.InitialLoadState
import com.example.nasren.weedmapschallenge.models.Business
import com.example.nasren.weedmapschallenge.viewmodels.FeedViewModel
import com.jakewharton.rxbinding.widget.RxTextView
import kotlinx.android.synthetic.main.activity_feed.*
import java.util.concurrent.TimeUnit

class FeedActivity : AppCompatActivity() {

    private lateinit var feedViewModel: FeedViewModel
    private lateinit var businessAdapter: BusinessAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)
        initViewModel()
        initView()
    }

    private fun initView() {
        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        businessAdapter = BusinessAdapter(feedViewModel)
        rvFeed.layoutManager = GridLayoutManager(this, 2)
        rvFeed.adapter = businessAdapter
    }

    private fun initViewModel() {
        feedViewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

    private fun initListeners() {
        // http://reactivex.io/documentation/operators/debounce.html
        RxTextView.textChanges(etSearch).debounce(500, TimeUnit.MILLISECONDS)
                                        .map { str -> str.toString() }
            .subscribe { s ->
                runOnUiThread {
                    if (s.isEmpty())
                        // when the string is empty show results for "weed"
                        getBusinesses("weed")
                    else
                        getBusinesses(s)
                }
            }
    }

    private fun getBusinesses(s: String) {
        //hardcoded the coordinates for simplicity
        feedViewModel.search(s, 33.666, -117.7564)

        feedViewModel.businessList
            .observe(this, Observer<PagedList<Business>> { list ->
                if (list != null)
                    businessAdapter.submitList(list)
            })

        //show the correct ui state based on the state of the initial load
        feedViewModel.initialLoadStatus?.observe(this, Observer { status ->
            when (status) {
                is InitialLoadState.Loading -> showProgressBar()
                is InitialLoadState.Failed -> showError(status.message)
                is InitialLoadState.Success -> showRecyclerView()
            }
        })
    }

    private fun showRecyclerView() {
        rvFeed.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        pbInitialLoading.visibility = View.GONE
    }

    private fun showError(error: String?) {
        rvFeed.visibility = View.GONE
        tvError.visibility = View.VISIBLE
        pbInitialLoading.visibility = View.GONE

        tvError.text = error
    }

    private fun showProgressBar() {
        rvFeed.visibility = View.GONE
        tvError.visibility = View.GONE
        pbInitialLoading.visibility = View.VISIBLE
    }
}