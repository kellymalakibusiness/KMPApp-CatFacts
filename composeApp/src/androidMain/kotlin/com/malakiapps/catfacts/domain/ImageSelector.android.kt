package com.malakiapps.catfacts.domain

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.malakiapps.catfacts.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.ByteArrayOutputStream
import java.io.InputStream

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ImageSelector(private val mainActivity: MainActivity) {
    var _image = MutableStateFlow<ByteArray?>(null)
    actual val image: StateFlow<ByteArray?> = _image.asStateFlow()
    private val pickMedia = mainActivity.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uriResult ->
        val imageByteArray = uriResult?.let { uri ->
            mainActivity.convertImageToByteArray(uri)
        }
        _image.update { imageByteArray }
    }
    actual suspend fun launchImagePicker() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}

private fun MainActivity.convertImageToByteArray(uri: Uri): ByteArray? {
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