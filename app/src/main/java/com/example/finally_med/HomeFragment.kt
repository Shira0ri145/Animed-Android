package com.example.finally_med

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class HomeFragment : Fragment() {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var cv1: CardView? = null
    var cv2: CardView? = null
    var cv3: CardView? = null
    var cardsearch: CardView? = null
    var phone = "111-111-1111"

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


        var view =  inflater.inflate(R.layout.fragment_home, container, false)

        cv1 = view.findViewById<CardView>(R.id.guideCard)

        cv1!!.setOnClickListener{
            val frag = GuideFragment() //navigate to guide
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fram_layout,frag)?.commit()

        }

        cardsearch = view.findViewById<CardView>(R.id.searchView)

        cardsearch!!.setOnClickListener{
            val searchfrag = SearchFragment() //navigate to guide
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fram_layout,searchfrag)?.commit()
        }

        cv2 = view.findViewById<CardView>(R.id.FavCard)

        cv2!!.setOnClickListener{
            val Favfrag = FavoriteFragment() //navigate to guide
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fram_layout,Favfrag)?.commit()
        }

        cv3 = view.findViewById<CardView>(R.id.MapCard)

        cv3!!.setOnClickListener(View.OnClickListener { view ->


            val gmmIntentUri = Uri.parse("geo:0,0?q=ร้านขายยาสัตว์")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)

        })







        return view


    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}