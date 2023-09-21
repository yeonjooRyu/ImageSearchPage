package com.example.imagesearchpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearchpage.databinding.ActivityMainBinding
import com.example.imagesearchpage.databinding.FragmentSearchBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var likedItems: ArrayList<SearchItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.run {
            btnHome.setOnClickListener {
                setFragment(SearchFragment())
            }
            btnInbox.setOnClickListener {
                setFragment(InboxFragment())
            }
        }
        setFragment(SearchFragment())

    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.frameLayout, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

    fun addLikedItem(item: SearchItemModel) {
        if(!likedItems.contains(item)) {
            likedItems.add(item)
        }
    }

    fun removeLikedItem(item: SearchItemModel) {
        likedItems.remove(item)
    }



}