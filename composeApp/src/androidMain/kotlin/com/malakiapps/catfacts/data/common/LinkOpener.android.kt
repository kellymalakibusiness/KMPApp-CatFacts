package com.malakiapps.catfacts.data.common

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class LinkOpener(private val context: Context) {

    actual fun openLink(link: String) {
        val urlIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
            )

        context.startActivity(urlIntent)
    }
}