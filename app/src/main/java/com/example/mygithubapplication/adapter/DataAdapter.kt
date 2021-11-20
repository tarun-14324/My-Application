package com.example.mygithubapplication.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygithubapplication.DataBase.UserEntity
import com.example.mygithubapplication.R
import com.example.mygithubapplication.activites.DetailActivity


class DataAdapter(): RecyclerView.Adapter<DataAdapter.MyViewHolder>() {

    var items = ArrayList<UserEntity>()
lateinit var context:Context
    fun setListData(context: Context,data: ArrayList<UserEntity>) {
        this.items = data
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.repo_items, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        holder.bind(context,items[position])

    }


    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
            val tvDesc: TextView = itemView.findViewById(R.id.tvDesc)
            val imageThumb: ImageView = itemView.findViewById(R.id.imageThumb)
            val moveBtn: ImageView = itemView.findViewById(R.id.next)

            fun bind(context:Context,data: UserEntity) {
                tvTitle.text = data.name
                if (!TextUtils.isEmpty(data.desc)) {
                    tvDesc.text = data.desc
                } else {
                    tvDesc.text = "No Desc available."
                }


                val url = data.url
                Glide.with(imageThumb)
                    .load(url)
                    .circleCrop()
                    .placeholder(R.drawable.default_thumb)
                    .error(R.drawable.default_thumb)
                    .fallback(R.drawable.default_thumb)
                    .into(imageThumb)
                moveBtn.setOnClickListener {
                    val intent: Intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("name", data.name)
                    intent.putExtra("url", data.url)
                    intent.putExtra("repos_url", data.url)
                    intent.putExtra("desc", data.desc)
                    intent.putExtra("id", data.id)
                    context.startActivity(intent)
                }
            }

    }
}