package com.example.finally_med

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import android.content.Intent

import android.widget.*


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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_search, container, false)

        val search = v.findViewById<SearchView>(R.id.search)
        val values = arrayOf(
            "Agita 1 GB", "Altresyn 540ml", "Baycox", "Citius 5", "Cobactan")
        val mListView = v.findViewById<ListView>(R.id.listView)




        val adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, values) }

        mListView.adapter = adapter

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                search.clearFocus()
                if(values.contains(p0))
                {
                    adapter?.filter?.filter(p0)
                }else{
                    Toast.makeText(context?.applicationContext, "Item Not Found!", Toast.LENGTH_LONG).show()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter?.filter?.filter(p0)
                return false
            }

        })


        mListView.setOnItemClickListener{parent, view, position, id ->



            if(mListView.getItemAtPosition(position).toString() == "Agita 1 GB"){
                val intent = Intent(activity, AgitaActivity::class.java)
                startActivity(intent)


            }
            if(mListView.getItemAtPosition(position).toString() == "Altresyn 540ml"){
                val intent = Intent(activity, AltresynActivity::class.java)
                startActivity(intent)
            }
            if(mListView.getItemAtPosition(position).toString() == "Baycox"){
                val intent = Intent(activity, BaycoxActivity::class.java)
                startActivity(intent)
            }
            if(mListView.getItemAtPosition(position).toString() == "Citius 5"){
                val intent = Intent(activity, CitriusActivity::class.java)
                startActivity(intent)
            }
            if(mListView.getItemAtPosition(position).toString() == "Cobactan"){
                val intent = Intent(activity, CobactanActivity::class.java)
                startActivity(intent)
            }

        }



        return v
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
}