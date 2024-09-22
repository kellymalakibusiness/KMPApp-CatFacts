package com.malakiapps.catfacts.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.domain.useCases.QueryFactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val queryFactsUseCase: QueryFactsUseCase
): ViewModel() {
    private val _catFacts = MutableStateFlow<List<CatFact?>>(emptyList())
    val catFacts: StateFlow<List<CatFact?>> = _catFacts.asStateFlow()

    init {
        refreshFacts()
    }

    fun refreshFacts(){
        viewModelScope.launch {
            val newFacts = withContext(Dispatchers.IO){
                queryFactsUseCase.invoke()
            }
            _catFacts.value = buildList {
                addAll(
                    catFacts.value.mapNotNull {
                    it
                }
                )
                addAll(newFacts)
                add(null)
            }
        }

    }
}