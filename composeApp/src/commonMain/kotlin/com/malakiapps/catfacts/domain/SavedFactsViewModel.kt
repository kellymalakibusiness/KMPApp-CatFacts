package com.malakiapps.catfacts.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedFactsViewModel(
    private val localStorageRepository: LocalStorageRepository,
): ViewModel() {
    private val _savedFacts: MutableStateFlow<List<CatFact>> = MutableStateFlow(emptyList())
    val savedFacts: StateFlow<List<CatFact>> = _savedFacts.asStateFlow()

    init {
        getSavedFacts()
    }

    private fun getSavedFacts(){
        viewModelScope.launch {
            localStorageRepository.getSavedFacts().collect { facts ->
                //_savedFacts.update { facts }
                _savedFacts.value = facts
            }
        }
    }

    fun onDeleteFact(catFact: CatFact){
        viewModelScope.launch {
            localStorageRepository.onDeleteSavedFact(catFact = catFact)
        }
    }
}