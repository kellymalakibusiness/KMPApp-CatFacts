package com.malakiapps.catfacts.data.fact

import kotlinx.serialization.Serializable

@Serializable
data class CatFactListDOT(
    val data: List<String>
)
