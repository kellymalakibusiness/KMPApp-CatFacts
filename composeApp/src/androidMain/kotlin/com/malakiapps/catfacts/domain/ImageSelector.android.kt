package com.malakiapps.catfacts.domain

import android.net.Uri
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.malakiapps.catfacts.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class ImageSelector(private val mainActivity: MainActivity) {

    actual val image: MutableStateFlow<ByteArray?> = mainActivity.image

    actual suspend fun launchImagePicker() {
        mainActivity.pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}