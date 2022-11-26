package com.example.finally_med

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finally_med.ml.AnimaxNomd
import com.example.finally_med.ml.AnimedClassifierAugmented
import com.example.finally_med.ml.TmModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder


class CamActivity : AppCompatActivity() {

    var confidence : TextView? = null
    var result: TextView? = null
    var imageView: ImageView? = null
    var imageSize = 180
    private val image: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cam)

        val btnCamera = findViewById<Button>(R.id.btnCamera)
        result = findViewById(R.id.predictView)
        confidence = findViewById(R.id.confidence)
        imageView = findViewById(R.id.ivImage)
        val btnGallery = findViewById<Button>(R.id.btnGallery)


        btnCamera.setOnClickListener(View.OnClickListener {
            // Launch camera if we have permission
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1)
            } else {
                //Request camera permission if we don't have it.
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        })
        btnGallery.setOnClickListener(View.OnClickListener { // Launch camera if we have permission
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, 2)
        })



    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2) {
            run {
                val uri = data!!.data
                imageView!!.setImageURI(uri)
                try {
                    val image =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    classifyImage(image)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            val image = data!!.extras!!["data"] as Bitmap?
            imageView!!.setImageBitmap(image)
            classifyImage(image)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun classifyImage(image: Bitmap?) {
        var image = image
        try {
            image = Bitmap.createScaledBitmap(image!!, imageSize, imageSize, false)
            val model: AnimaxNomd = AnimaxNomd.newInstance(
                applicationContext
            )

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(
                intArrayOf(1, imageSize, imageSize, 3),
                DataType.FLOAT32
            )
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            // get 1D array of 224 * 224 pixels in image
            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

            // iterate over pixels and extract R, G, and B values. Add to bytebuffer.
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val `val` = intValues[pixel++] // RGB
                    byteBuffer.putFloat((`val` shr 16 and 0xFF) * 1f)//(1f / 255f))
                    byteBuffer.putFloat((`val` shr 8 and 0xFF) * 1f)//(1f / 255f))
                    byteBuffer.putFloat((`val` and 0xFF) * 1f)//(1f / 255f))
                }
            }
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs: AnimaxNomd.Outputs = model.process(inputFeature0)
            val outputFeature0: TensorBuffer = outputs.getOutputFeature0AsTensorBuffer()
            val confidences = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Agita 1 GB", "Altresyn 540 ml", "Baycox", "Citius 5", "Cobactan")
            result!!.text = classes[maxPos]

            var s = ""
            for (i in classes.indices) {
                s += String.format("%s:    %.1f%%\n", classes[i], confidences[i] * 100)
            }

            confidence!!.text = s

            val Btnfind = findViewById<Button>(R.id.Btnfind)


            Btnfind.setOnClickListener(View.OnClickListener {
                if (result!!.text.toString() == "Agita 1 GB"){
                    val intent = Intent(this, AgitaActivity::class.java)
                    startActivity(intent)
                }

                if (result!!.text.toString() == "Altresyn 540 ml"){
                    val intent = Intent(this, AltresynActivity::class.java)
                    startActivity(intent)
                }

                if (result!!.text.toString() == "Baycox"){
                    val intent = Intent(this, BaycoxActivity::class.java)
                    startActivity(intent)
                }

                if (result!!.text.toString() == "Citius 5"){
                    val intent = Intent(this, CitriusActivity::class.java)
                    startActivity(intent)
                }

                if (result!!.text.toString() == "Cobactan"){
                    val intent = Intent(this, CobactanActivity::class.java)
                    startActivity(intent)
                }


            })


            // Releases model resources if no longer used.
            model.close()
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }
}