package com.example.finally_med

import android.Manifest
import android.R.attr
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.ActivityCompat.startActivityForResult
import android.provider.MediaStore
import android.content.Intent
import android.widget.ImageView
import androidx.core.app.ActivityCompat.startActivityForResult
import android.graphics.BitmapFactory

import android.graphics.Bitmap

import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CamFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CamFragment : Fragment() {
    private val REQUEST_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PICK_IMAGE = 2
    lateinit var ivImage: ImageView


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

        val v = inflater.inflate(R.layout.fragment_cam, container, false)
        val btCapture = v.findViewById<Button>(R.id.btCapturePhoto)
        val btOpen = v.findViewById<Button>(R.id.btOpenGallery)


        """btCapture.setOnClickListener {
            openCamera()
        }"""





        return v
    }

    private fun openCamera() {   // oncreateview method in fragment

        """Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(PackageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }"""
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()  //ดึงmethod ด้านล่างมาใช้
    }

    private fun checkCameraPermission() {
        if (activity?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) }
            != PackageManager.PERMISSION_GRANTED) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_PERMISSION)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        """if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            ivImage.setImageBitmap(imageBitmap)
        }else {
            val uri = data?.getData()
            ivImage.setImageURI(uri)

        }"""

            }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CamFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CamFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}