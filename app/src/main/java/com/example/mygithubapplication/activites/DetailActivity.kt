package com.example.mygithubapplication.activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.mygithubapplication.R
import com.example.mygithubapplication.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        val int: Intent = intent
        val name: String? = int.getStringExtra("name")
        val repoLink: String? = int.getStringExtra("repos_url")
        val id: String? = int.getStringExtra("id")
        val imageUrl: String? = int.getStringExtra("url")
        val desc: String? = int.getStringExtra("desc")


        if (name.isNullOrEmpty()) {
            binding.tvName.text = id
        } else {
            binding.tvName.text = name
        }
        binding.tvLink.text = repoLink

        if (!TextUtils.isEmpty(desc)) {
            binding.tvDesc.text = desc
        } else {
            binding.tvDesc.text = "No Desc available."
        }
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(binding.repoIv)
        binding.tvLink.setOnClickListener {
            binding.appbar.visibility=View.GONE
            binding.card.visibility=View.GONE
            webView(repoLink)
        }
        binding.backBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun webView(link: String?) {

        binding.webview.webViewClient = WebViewClient()

        binding.webview.visibility = View.VISIBLE
        if (link != null) {
            binding.webview.loadUrl(link)
        }
    }


}
