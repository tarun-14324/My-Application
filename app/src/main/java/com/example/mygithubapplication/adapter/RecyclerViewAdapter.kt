package com.example.mygithubapplication.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubapplication.DataBase.RoomAppDb
import com.example.mygithubapplication.DataBase.UserEntity
import com.example.mygithubapplication.R
import com.example.mygithubapplication.activites.DetailActivity
import com.example.mygithubapplication.dataSource.RecyclerData


class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    var items = ArrayList<RecyclerData>()
    lateinit var mctx:Context
    fun setListData(context: Context, data: ArrayList<RecyclerData>) {
        this.items = data
        this.mctx=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.repo_items, parent, false)

        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position],mctx)

    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val tvTitle:TextView =view.findViewById(R.id.tvTitle)
        val tvDesc:TextView = view.findViewById(R.id.tvDesc)
        val imageThumb :ImageView= view.findViewById(R.id.imageThumb)
        val next:ImageView=view.findViewById(R.id.next)

        fun bind(data: RecyclerData,context: Context) {
            tvTitle.text = data.name
            if(!TextUtils.isEmpty(data.description)) {
                tvDesc.text = data.description
            } else {
                tvDesc.text = "No Desc available."
            }

            val url = data.owner.avatar_url
            Glide.with(imageThumb)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.default_thumb)
                .error(R.drawable.default_thumb)
                .fallback(R.drawable.default_thumb)
                .into(imageThumb)
            next.setOnClickListener {
                val intent: Intent =Intent(context,DetailActivity::class.java)
                intent.putExtra("name",data.name)
                intent.putExtra("url",data.owner.avatar_url)
                intent.putExtra("repos_url",data.owner.html_url)
                intent.putExtra("desc",data.description)
                intent.putExtra("id",data.owner.id)
                context.startActivity(intent)
            }
            val userDao = RoomAppDb.getAppDatabase(context)
            val user=UserEntity(0,data.name,data.owner.repos_url,data.owner.html_url,data.name,data.owner.id.toString())
            userDao?.userDao()?.insertUser(user)
        }

    }
}





