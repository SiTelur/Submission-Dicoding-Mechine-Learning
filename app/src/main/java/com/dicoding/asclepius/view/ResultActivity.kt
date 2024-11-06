package com.dicoding.asclepius.view

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dicoding.asclepius.data.source.local.entity.AnalyzeHistory
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.util.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import kotlin.text.format

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private var currentImageUri: Uri? = null

    private lateinit var imageClassifierHelper: ImageClassifierHelper
   private val resultActivityViewwModel by viewModels<ResultViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // TODO: Menampilkan hasil gambar, prediksi, dan confidence score.
        currentImageUri = Uri.parse(intent.getStringExtra(SEND_IMAGE))
        showImage()




        imageClassifierHelper = ImageClassifierHelper(context = this, classifierListener = object :
            ImageClassifierHelper.ClassifierListener {
            override fun onError(error: String) {
                runOnUiThread {
                    Toast.makeText(this@ResultActivity, error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                runOnUiThread {
                    results?.let { it ->
                        if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                            println(it)
                            val displayResult =
                                    "${ it[0].categories[0].label} " + NumberFormat.getPercentInstance()
                                        .format(it[0].categories[0].score).trim()
                            binding.resultText.text  = displayResult
                            resultActivityViewwModel.insertData(AnalyzeHistory(imageUri = currentImageUri.toString(), resultRate = displayResult, insertedDate = getCurrentDateAsString()))
                        } else {
                            binding.resultText.text = ""
                        }
                    }
                }
            }

        })

        imageClassifierHelper.classifyStaticImage(currentImageUri!!)
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(currentImageUri)
        }
    }

    companion object {
        const val SEND_IMAGE = "sendImage"
    }

    fun getCurrentDateAsString(): String {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(date)
    }


}