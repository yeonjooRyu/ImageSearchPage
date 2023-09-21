package com.example.imagesearchpage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.imagesearchpage.databinding.ItemBinding
import java.util.ArrayList

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var items = ArrayList<SearchItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.image.setImageResource(items[position].image)
        holder.title.text = items[position].title
        holder.dateTime.text = items[position].dateTime
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var image: ImageView = binding.image
        var title: TextView = binding.tvTitle
        var dateTime: TextView = binding.tvDatetime

    }
}