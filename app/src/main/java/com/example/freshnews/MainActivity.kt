package com.example.freshnews

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freshnews.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()
        newsAdapter = NewsAdapter {
            onNewsClicked(it.url)
        }
        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = newsAdapter
        }

        binding.refresh.setOnRefreshListener {
            loadNews()
            binding.refresh.isRefreshing = false
        }
    }

    //Note: To launch web browser uncomment this
    /*val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(Uri.parse(uri), "text/plain")
    startActivity(intent)*/
    private fun onNewsClicked(uri: String) {
        val builder = CustomTabsIntent.Builder()
        val defaultColors = CustomTabColorSchemeParams.Builder().setToolbarColor(ContextCompat.getColor(this, R.color.purple_700)).build()
        builder.setDefaultColorSchemeParams(defaultColors)
        builder.build().launchUrl(this, Uri.parse(uri))
    }

    private fun loadNews() {
        binding.progress.visibility = View.VISIBLE
        val memes = RetrofitClientInstance.newsInstance.getNews("in", 1)
        memes.enqueue(object : Callback<Article> {

            override fun onResponse(call: Call<Article>, response: Response<Article>) {
                newsAdapter.submitList(response.body()?.articles)
                binding.progress.visibility = View.GONE
            }

            override fun onFailure(call: Call<Article>, t: Throwable) {
                binding.progress.visibility = View.GONE
                Toast.makeText(this@MainActivity, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                Log.d("NEWS", getString(R.string.something_went_wrong))
            }
        })
    }
}