package com.malakiapps.catfacts.domain

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlinx.cinterop.reinterpret as reinterpret1

actual class ImageSelector {
    actual val image: MutableStateFlow<ByteArray?> = MutableStateFlow(null)


    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun launchImagePicker(){
        image.value = suspendCancellableCoroutine { continuation ->
            val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
                ?: return@suspendCancellableCoroutine continuation.resumeWithException(
                    IllegalStateException("No root view controller found")
                )

            val config = PHPickerConfiguration().apply {
                filter = PHPickerFilter.imagesFilter
                selectionLimit = 1 // Single selection
            }

            val picker = PHPickerViewController(configuration = config)
            picker.delegate = object : NSObject(), PHPickerViewControllerDelegateProtocol {
                override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                    // Dismiss the picker once done
                    picker.dismissViewControllerAnimated(true, null)

                    val result = didFinishPicking.firstOrNull() as? PHPickerResult
                    val provider = result?.itemProvider

                    if (provider != null && provider.hasItemConformingToTypeIdentifier("public.image")) {
                        provider.loadDataRepresentationForTypeIdentifier("public.image") { data, error ->

                            if (error != null) {
                                continuation.resumeWithException(Exception("An errror occurred while reading an image: Error: $error"))
                                return@loadDataRepresentationForTypeIdentifier
                            }
                            continuation.resume(data?.toByteArray())
                        }
                    } else {
                        continuation.resume(null)
                    }
                }
            }

            viewController.presentViewController(picker, animated = true, completion = null)

            // Cancel the coroutine if needed
            continuation.invokeOnCancellation {
                picker.dismissViewControllerAnimated(true, null)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun NSData.toByteArray(): ByteArray {
        val bytes = this.bytes?.reinterpret1<ByteVar>()
        return bytes?.let { ByteArray(this.length.toInt()) { index -> bytes[index] } } ?: ByteArray(0)
    }
}