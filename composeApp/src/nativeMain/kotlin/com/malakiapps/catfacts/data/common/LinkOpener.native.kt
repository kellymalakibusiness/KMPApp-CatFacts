package com.malakiapps.catfacts.data.common

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class LinkOpener {
    actual fun openLink(link: String) {
        val nsUrl = NSURL(string = link)
        UIApplication.sharedApplication.openURL(nsUrl)
    }
}