package com.malakiapps.catfacts.domain

import androidx.lifecycle.ViewModel
import com.malakiapps.catfacts.data.common.LinkOpener

class AboutViewModel(
    private val linkOpener: LinkOpener
): ViewModel() {

    fun openLink(link: String){
        linkOpener.openLink(link)
    }
}