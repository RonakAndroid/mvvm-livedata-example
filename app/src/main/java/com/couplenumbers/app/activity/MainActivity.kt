package com.couplenumbers.app.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.couplenumbers.app.R
import com.couplenumbers.app.adapter.NumbersAdapter
import com.couplenumbers.app.model.NumberModel
import com.couplenumbers.app.utils.Resource
import com.couplenumbers.app.utils.Status
import com.couplenumbers.app.viewmodel.NumberViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val numberAdapter by lazy { NumbersAdapter() }
    private val linearLayoutManager by lazy { LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) }

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(NumberViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNumberRecyclerView()
        viewModel.numberLiveData.observe(this, Observer(this::handleNumberList))
        viewModel.callAPI()
    }

    private fun handleNumberList(response: Resource<MutableList<NumberModel.Number>>?) {
        response?.let {
            when (response.status) {
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.ERROR -> handleError()
                Status.SUCCESS -> handleSuccess(response.item)
            }
        }
    }

    private fun handleSuccess(arrayList: MutableList<NumberModel.Number>?) {
        progressBar.visibility = View.GONE
        numberAdapter.updateList(arrayList)
    }

    private fun handleError() {
        tvError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun setupNumberRecyclerView() {
        rvNumber.layoutManager = linearLayoutManager
        rvNumber.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        rvNumber.adapter = numberAdapter
    }
}
