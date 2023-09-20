package com.example.imagesearchpage

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearchpage.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private val dataList = mutableListOf<SearchItemModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            dataList.add(SearchItemModel(R.drawable.sample_0,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_1,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_2,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_3,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_4,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_5,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_6,"[Image]스포츠 서울","2023-09-06 21:30:05", false))
            dataList.add(SearchItemModel(R.drawable.sample_7,"[Image]스포츠 서울","2023-09-06 21:30:05", false))


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val adapter = SearchAdapter(dataList)

        searchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        searchBinding.searchRecyclerview.adapter = adapter
        searchBinding.searchRecyclerview.layoutManager = GridLayoutManager(context, 2)
        Log.d("test","datalist = $dataList")
        searchBinding.btnSearch.setOnClickListener{
            saveData()
            Toast.makeText(requireContext(), "Image Search!", Toast.LENGTH_SHORT).show()
        }
        loadData()
        return searchBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun saveData(){
        val pref = requireContext().getSharedPreferences("pref", Activity.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("name", searchBinding.etSearch.text.toString())
        edit.apply()
    }

    private fun loadData(){
        val pref = requireContext().getSharedPreferences("pref", Activity.MODE_PRIVATE)
        searchBinding.etSearch.setText(pref.getString("name", ""))
    }




}