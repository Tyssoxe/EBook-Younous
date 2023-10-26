package com.example.ebook_younous

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ebookprojetfinal.R

class BookListViewAdapter (var data: MutableList<Items>, var bookItemCallback: BookItemCallback) : RecyclerView.Adapter<BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_item, parent, false)
        return BookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(data[position], bookItemCallback)
    }
}