package com.example.imagesearchpage

import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearchpage.databinding.ItemBinding
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class SearchAdapter(private val searchContext : Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var items = ArrayList<SearchItemModel>()

    fun clearItem(){
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(searchContext)
            .load(items[position].url)
            .into(holder.image)

        holder.like.visibility = if (items[position].isLike) View.VISIBLE else View.INVISIBLE
        holder.title.text = items[position].title
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

    inner class ViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        var image: ImageView = binding.image
        var like: ImageView = binding.like
        var title: TextView = binding.tvTitle
        var dateTime: TextView = binding.tvDatetime
        val item: ConstraintLayout = binding.item

        init {
            like.visibility = View.GONE
            image.setOnClickListener(this)
            item.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION } ?: return
            val item = items[position]

            item.isLike = !item.isLike

            if (item.isLike) {
                (searchContext as MainActivity).addLikedItem(item)
                Snackbar.make(binding.item, "보관함에 추가되었습니다.", Snackbar.LENGTH_SHORT).show()
            } else {
                (searchContext as MainActivity).removeLikedItem(item)
            }
            notifyItemChanged(position)
        }
    }




}