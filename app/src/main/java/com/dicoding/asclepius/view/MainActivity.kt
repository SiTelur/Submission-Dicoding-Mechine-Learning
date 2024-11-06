package com.dicoding.asclepius.view

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.dicoding.asclepius.R
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentImageUri: Uri? = null

    private val uCropContract = object: ActivityResultContract<List<Uri>, Uri>(){
        override fun createIntent(context: Context, input: List<Uri>): Intent {
            val inputUri =  input[0]
            val outPut =  input[1]

            val uCrop = UCrop.of(inputUri,outPut)
            return uCrop.getIntent(context)

        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri {
           if (resultCode == RESULT_OK) {
               return UCrop.getOutput(intent!!)!!
           }
            showToast("Anda Batal Memilih Gambar")
            return Uri.EMPTY
        }
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
        if (uri == null ) {
            showToast("Anda Belum Memilih Foto")
            return@registerForActivityResult
        }
        val inputUri = uri
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat.getDateTimeInstance()
        val formatedDate = formatter.format(date)
        val outputUri = File(filesDir, "${formatedDate}.jpg").toUri()

        val listUri = listOf(inputUri!!,outputUri)
        cropImage.launch(listUri)
    }

    private val cropImage = registerForActivityResult(uCropContract){uri->
        currentImageUri=uri
        binding.previewImageView.setImageURI(currentImageUri)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }
        binding.btnHistory.setOnClickListener { moveToHistory() }
        binding.btnNews.setOnClickListener { moveToNews() }
    }

    private fun startGallery() {
        // TODO: Mendapatkan gambar dari Gallery.
        //launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        getContent.launch("image/*")
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        if (currentImageUri !=  null) {
            moveToResult()
        }else {
            showToast("Anda Belum Memilih Foto")
        }
    }

    private fun moveToResult() {
        if (currentImageUri != Uri.EMPTY) {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(ResultActivity.SEND_IMAGE,currentImageUri.toString())
            startActivity(intent)
        }else{
            showToast("Anda Belum Memilih Gambar")
        }
    }

    private fun moveToHistory(){
        val intent = Intent(this, HistoryActivity::class.java)
        startActivity(intent)
    }

    private fun moveToNews(){
        val intent = Intent(this, NewsActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun openCropActivity(sourceUri: Uri, destinationUri: Uri) {
        UCrop.of(sourceUri, destinationUri)
            .withMaxResultSize(maxWidth, maxHeight)
            .withAspectRatio(5f, 5f)
            .start(this)
    }






}