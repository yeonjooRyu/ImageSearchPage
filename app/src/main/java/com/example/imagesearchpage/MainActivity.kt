package com.example.imagesearchpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.imagesearchpage.databinding.ActivityMainBinding
import com.example.imagesearchpage.databinding.FragmentSearchBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
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


}