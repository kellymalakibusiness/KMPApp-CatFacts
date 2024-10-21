package com.malakiapps.catfacts

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import com.malakiapps.catfacts.common.di.initKoin
import com.malakiapps.catfacts.ui.App
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.ext.koin.androidContext
import java.io.ByteArrayOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {
    val image: MutableStateFlow<ByteArray?> = MutableStateFlow(null)

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriResult ->
        val imageByteArray = uriResult?.let { uri ->
            convertImageToByteArray(uri)
        }
        image.update { imageByteArray }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin {
            androidContext(this@MainActivity)
        }
        setContent {
            App()
        }
    }

    private fun convertImageToByteArray(uri: Uri): ByteArray? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val byteArrayOutputStream = ByteArrayOutputStream()
            inputStream?.copyTo(byteArrayOutputStream)
            inputStream?.close()
            byteArrayOutputStream.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}