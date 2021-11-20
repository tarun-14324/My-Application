package com.example.mygithubapplication.dataSource


data class RecyclerList(val items: ArrayList<RecyclerData>)
data class RecyclerData(val name: String, val description: String, val owner: Owner)
data class Owner(val avatar_url: String, val html_url: String, val repos_url: String, val id: Int)
