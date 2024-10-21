package com.malakiapps.catfacts.domain

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.refTo
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSData
import platform.Foundation.NSDictionary
import platform.UIKit.UIImage
import platform.UIKit.UIImagePNGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.darwin.NSObject
import platform.posix.memmove
import kotlin.coroutines.resume

actual class ImageSelector/*(private val viewController: UIViewController): NSObject(),
    UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol*/ {

    actual val image: MutableStateFlow<ByteArray?> = MutableStateFlow(null)
    //private var continuation: CancellableContinuation<ByteArray?>? = null

    actual suspend fun launchImagePicker() {
        /*val byteArray = suspendCancellableCoroutine { cont ->
            continuation = cont
            val picker = UIImagePickerController()
            picker.sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary
            picker.delegate = this
            //viewController.presentViewController(picker, animated = true, completion = null)
        }
        _image.update { byteArray }*/
    }

    /*@ObjCAction
    fun imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo: NSDictionary) {
        picker.dismissViewControllerAnimated(true, completion = null)

        val image = didFinishPickingMediaWithInfo.objectForKey(UIImagePickerControllerOriginalImage) as? UIImage
        val data = image?.toNSData()?.toByteArray()

        continuation?.resume(data)
        continuation = null
    }

    @ObjCAction
    override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
        picker.dismissViewControllerAnimated(true, completion = null)
        continuation?.resume(null)
        continuation = null
    }

// Extension to convert UIImage to NSData and then ByteArray
fun UIImage.toNSData(): NSData? = UIImagePNGRepresentation(this)

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val bytes = ByteArray(this.length.toInt())
    memmove(bytes.refTo(0), this.bytes, this.length)
    return bytes
}*/
}