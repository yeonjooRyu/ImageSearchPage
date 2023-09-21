package com.example.imagesearchpage

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imagesearchpage.RetrofitClient.apiService
import com.example.imagesearchpage.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private lateinit var searchBinding: FragmentSearchBinding
    private var dataList : ArrayList<SearchItemModel> = ArrayList()
    private lateinit var adapter : SearchAdapter
    private lateinit var searchContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        searchContext  = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = SearchAdapter(searchContext)

        searchBinding = FragmentSearchBinding.inflate(inflater,container,false)
        searchBinding.searchRecyclerview.adapter = adapter
        searchBinding.searchRecyclerview.layoutManager = GridLayoutManager(context, 2)
        Log.d("test","datalist = $dataList")
        searchBinding.btnSearch.setOnClickListener{
            val query = searchBinding.etSearch.text.toString()
            if(query.isNotEmpty()){
                saveData()
                adapter.clearItem()
                imageSearch(query)
                Toast.makeText(requireContext(), "Image Search!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Not entered!", Toast.LENGTH_SHORT).show()
            }

            val hideKeyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideKeyboard.hideSoftInputFromWindow(searchBinding.etSearch.windowToken, 0)


        }
        loadData()
        return searchBinding.root

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
                            Log.d("checkImage","이미지 확인 ${url}")

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