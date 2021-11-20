package com.example.mygithubapplication.Api

import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor


class RetroInstance {

    companion object {
        val baseURL = "https://api.github.com/search/"
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}



