package com.example.mygithubapplication.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubapplication.Api.GitHubUserService
import com.example.mygithubapplication.Api.RetroInstance
import com.example.mygithubapplication.dataSource.RecyclerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel : ViewModel() {

    var recyclerListData: MutableLiveData<RecyclerList> = MutableLiveData()


    suspend fun getRecyclerListDataObserver(): MutableLiveData<RecyclerList> {
        return withContext(Dispatchers.IO) { recyclerListData }
    }

    fun makeApiCall(input: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(GitHubUserService::class.java)
        val call = retroInstance.getDataFromAPI(input)
        call.enqueue(object : retrofit2.Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())


                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                recyclerListData.postValue(null)

            }
        })
    }


    fun getApiCall() {
        val rand = Random()
        val page = rand.nextInt(50)
        val retroInstance = RetroInstance.getRetroInstance().create(GitHubUserService::class.java)
        val call = retroInstance.CallDataFromAPI("Github", page, 10)
        call.enqueue(object : retrofit2.Callback<RecyclerList> {
            override fun onResponse(call: Call<RecyclerList>, response: Response<RecyclerList>) {
                if (response.isSuccessful) {
                    recyclerListData.postValue(response.body())



                } else {
                    recyclerListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<RecyclerList>, t: Throwable) {
                recyclerListData.postValue(null)
            }
        })
    }


}