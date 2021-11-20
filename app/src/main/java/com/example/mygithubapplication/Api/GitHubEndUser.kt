package com.example.mygithubapplication.Api

import com.example.mygithubapplication.dataSource.RecyclerList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubUserService {


    @GET("repositories")
     fun CallDataFromAPI(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<RecyclerList>


    @GET("repositories")
    fun getDataFromAPI(
        @Query("q") query: String,
    ): Call<RecyclerList>
}

