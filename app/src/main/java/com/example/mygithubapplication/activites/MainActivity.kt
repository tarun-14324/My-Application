package com.example.mygithubapplication.activites

import android.annotation.SuppressLint
import android.app.Service
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mygithubapplication.R
import com.example.mygithubapplication.ViewModel.DataBaseViewModel
import com.example.mygithubapplication.ViewModel.MainViewModel
import com.example.mygithubapplication.adapter.DataAdapter
import com.example.mygithubapplication.adapter.RecyclerViewAdapter
import com.example.mygithubapplication.dataSource.RecyclerList
import com.example.mygithubapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), ConnectivityManager.OnNetworkActiveListener {

    var context = this
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var DataBaseAdapter: DataAdapter
    lateinit var DataViewModel: DataBaseViewModel
    lateinit var viewModel: MainViewModel

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.N)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        DataViewModel = ViewModelProviders.of(this).get(DataBaseViewModel::class.java)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {

                    lifecycleScope.launch {
                        initRecyclerView()
                        createData()
                        viewModel.getApiCall()

                    }
                }
            } else {
                showNoNetSnackbar("Please check your internet connection and try again")

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    DataBaseAdapter = DataAdapter()
                    adapter = DataBaseAdapter
                    val divider = DividerItemDecoration(
                        applicationContext,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                    addItemDecoration(divider)
                }

                DataViewModel.getAllUsersObservers().observe(this, Observer {

                    DataBaseAdapter.setListData(this, ArrayList(it))
                    DataBaseAdapter.notifyDataSetChanged()


                })
            }
        }
        binding.refreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.getApiCall()
            Handler().postDelayed({
                binding.refreshLayout.setRefreshing(false)
            }, 2000)

        })


    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter()
            adapter = recyclerViewAdapter
            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
        }
    }


    suspend fun createData() {
        lifecycleScope.launch {
            viewModel.getRecyclerListDataObserver()
                .observe(this@MainActivity, object : Observer<RecyclerList?> {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onChanged(t: RecyclerList?) {
                        if (t != null) {
                            recyclerViewAdapter.setListData(this@MainActivity, t.items)
                            recyclerViewAdapter.notifyDataSetChanged()
                            binding.progressBar.visibility = View.GONE


                        } else {
                            Toast.makeText(
                                context,
                                "Error in getting data from api.",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                })
            binding.searchButton.setOnClickListener {
                if (binding.searchBoxId.text.isNullOrEmpty()) {
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.errorMessage.text = "please enter repository name"
                } else {
                    showLoading(true)
                    binding.errorMessage.visibility = View.INVISIBLE
                    viewModel.makeApiCall(binding.searchBoxId.text.toString())

                }
            }
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE

        } else {

            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoNetSnackbar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), "$msg", Snackbar.LENGTH_LONG).show()
    }

    fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNoNetSnackbar("isConnected")
    }

    override fun onNetworkActive() {
        TODO("Not yet implemented")
    }

}

















