package com.example.imagesearchpage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.example.imagesearchpage.databinding.ItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class InboxAdapter(var inboxContext: Context) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {
    var items = mutableListOf<SearchItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Glide.with(inboxContext)
                .load(items[position].url)
                .into((holder).image)

            holder.title.text = items[position].title
            holder.like.visibility = View.GONE
            holder.dateTime.text = convertTimeStampToDate(
                items[position].dateTime,
                "yyyy-MM-dd'T'HH:mm:ss.SSS+09:00",
                "yyyy-MM-dd HH:mm:ss"
            )

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var image: ImageView = binding.image
        var title: TextView = binding.tvTitle
        var dateTime: TextView = binding.tvDatetime
        var like: ImageView = binding.like
        val item:ConstraintLayout = binding.item

        init {
            like.visibility = View.GONE

            item.setOnClickListener {
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    items.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
        }

    }

    fun convertTimeStampToDate(timestamp: String?, fromFormatformat: String?, toFormatformat: String?) :String {
        var date: Date? = null
        var res = ""
        try {
            val format = SimpleDateFormat(fromFormatformat)
            date = format.parse(timestamp)
        } catch (e: ParseException){
            e.printStackTrace()
        }
        val sdf = SimpleDateFormat(toFormatformat)
        res = sdf.format(date)
        return res
    }
}