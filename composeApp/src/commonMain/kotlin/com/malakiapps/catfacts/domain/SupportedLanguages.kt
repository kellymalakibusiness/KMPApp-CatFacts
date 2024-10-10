package com.malakiapps.catfacts.domain

enum class SupportedLanguages(val isoCode: String, val label: String) {
    ENGLISH("eng", "English"),
    CZECH("cze", "Czech"),
    GERMAN("ger", "German"),
    SPANISH("esp", "Spanish"),
    RUSSIAN("rus", "Russian"),
    UKRAINIAN("ukr", "Ukrainian");

    companion object {
        private val _supportedLanguagesByIsoCode = entries.associateBy { it.isoCode }
        fun String.isoCodeToSupportedLanguage(): SupportedLanguages {
            return _supportedLanguagesByIsoCode[this] ?: throw Exception("Unsupported value $this found on the supportedLanguages isoKey")
        }
    }
}