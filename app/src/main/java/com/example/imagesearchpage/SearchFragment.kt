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
import com.example.imagesearchpage.RetrofitClient.apiService
import com.example.imagesearchpage.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
    private var dataList : ArrayList<SearchItemModel> = ArrayList()
    private lateinit var adapter : SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = SearchAdapter()

        searchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        searchBinding.searchRecyclerview.adapter = adapter
        searchBinding.searchRecyclerview.layoutManager = GridLayoutManager(context, 2)
        Log.d("test","datalist = $dataList")
        searchBinding.btnSearch.setOnClickListener{
            saveData()
            val query = searchBinding.etSearch.text.toString()
            imageSearch(query)
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

    private fun imageSearch(query:String){
        apiService.image_search(Constants.AUTH_HEADER, query, "recency", 1, 80)?.enqueue(object : Callback<ImageModel?>{
            override fun onResponse(call: Call<ImageModel?>, response: Response<ImageModel?>) {
                Log.d("searchFragment","response ${response.body()?.meta?.totalCount}")
                response.body()?.meta?.let {
                    if (it.totalCount > 0){
                        response.body()!!.documents.forEach {
                            val title = it.displaySitename
                            val dateTime = it.datetime
                            val url = it.thumbnailUrl

                            dataList.add(SearchItemModel(title, dateTime, url))
                        }
                    }
                    adapter.items = dataList
                    adapter.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<ImageModel?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }




}