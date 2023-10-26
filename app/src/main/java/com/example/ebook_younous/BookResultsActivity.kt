package com.example.ebook_younous

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ebookprojetfinal.R
import com.example.ebookprojetfinal.databinding.ActivityBookResultsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookResultsBinding.inflate(layoutInflater)
        var view: View = binding.root
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setContentView(view)
        setViewItems()
    }

    private fun setViewItems() {
        val searchInput = intent.getStringExtra("search")
        callService(searchInput.toString())
    }

    private fun callService(search: String) {
        val service: BookApi.BookService = BookApi().getClient().create(BookApi.BookService::class.java)
        val call: Call<BookApiResponse> = service.getBooks(search)
        call.enqueue(object : Callback<BookApiResponse> {
            override fun onResponse(call: Call<BookApiResponse>, response: Response<BookApiResponse>) {
                processResponse(response)
            }

            override fun onFailure(call: Call<BookApiResponse>, t: Throwable) {
                processFailure(t)
            }
        })
    }

    private fun processFailure(t: Throwable) {
        Toast.makeText(this, "Erreur", Toast.LENGTH_LONG).show()
        t.message?.let { Log.d("Erreur", it) }
    }

    private fun processResponse(response: Response<BookApiResponse>) {
        if (response.isSuccessful) {
            val body = response.body()
            if (body?.items!!.isNotEmpty()) {
                val adapter = BookListViewAdapter(body.items, object : BookItemCallback {
                    override fun onCellClick(data: Items) {
                        gotoNextActivity(data)
                    }
                })
                val recyclerView = findViewById<RecyclerView>(R.id.book_rv)
                binding.bookRv.adapter = adapter
                binding.bookRv.layoutManager = LinearLayoutManager(applicationContext)
            }
        }
    }


    private fun gotoNextActivity(data: Items) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(MainActivity.bookKey, data.volumeInfo?.title)
        intent.putExtra(MainActivity.imageKey, data.volumeInfo?.imageLinks?.thumbnail)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}