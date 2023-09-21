package com.example.imagesearchpage

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearchpage.databinding.FragmentInboxBinding

class InboxFragment : Fragment() {
    private lateinit var inboxContext: Context

    private var inboxBinding: FragmentInboxBinding? = null
    private lateinit var inboxAdapter: InboxAdapter

    private var inboxItems: List<SearchItemModel> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inboxContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_inbox, container, false)
        val mainActivity = activity as MainActivity
        inboxItems = mainActivity.likedItems

        Log.d("InboxFragment", "InboxItems Size = ${inboxItems.size}")

        inboxAdapter = InboxAdapter(inboxContext).apply {
            items = inboxItems.toMutableList()
        }

        inboxBinding = FragmentInboxBinding.inflate(inflater, container, false).apply {
            inboxRecyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            inboxRecyclerview.adapter = inboxAdapter
        }

        return inboxBinding?.root
    }


}