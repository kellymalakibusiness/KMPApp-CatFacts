package com.malakiapps.catfacts.domain

data class FactsSummary(
    val likedCount: Int,
    val dislikedCount: Int,
    val savedCount: Int,
    val savedFacts: List<CatFact>,
)
