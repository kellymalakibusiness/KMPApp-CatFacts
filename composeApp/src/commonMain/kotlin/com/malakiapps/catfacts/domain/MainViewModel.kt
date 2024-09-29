package com.malakiapps.catfacts.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malakiapps.catfacts.data.localDatabase.LocalStorageRepository
import com.malakiapps.catfacts.domain.useCases.QueryFactsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val queryFactsUseCase: QueryFactsUseCase,
    private val localStorageRepository: LocalStorageRepository
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
            _catFacts.update { currentValue ->
                buildList {
                    addAll(
                        currentValue.mapNotNull {
                            it
                        }
                    )
                    addAll(newFacts)
                    add(null)
                }
            }
        }
    }

    fun onFactLike(key: String, enabled: Boolean){
        _catFacts.update { currentList ->
            currentList.map { fact ->
                if(key == fact?.text){
                    fact.copy(liked = enabled)
                } else {
                    fact
                }
            }
        }
        viewModelScope.launch {
            localStorageRepository.onLikeFact(key, enabled)
        }
    }

    fun onFactDisLike(key: String, enabled: Boolean){
        println("Kelly: On dislike called")
        _catFacts.update { currentList ->
            currentList.map { fact ->
                if(key == fact?.text){
                    fact.copy(disliked = enabled)
                } else {
                    fact
                }
            }
        }
        viewModelScope.launch {
            localStorageRepository.onDisLikeFact(key, enabled)
        }
    }

    fun onFactDownload(catFact: CatFact, enabled: Boolean){
        _catFacts.update { currentList ->
            currentList.map { fact ->
                if(catFact.text == fact?.text){
                    fact.copy(downloaded = enabled)
                } else {
                    fact
                }
            }
        }
        viewModelScope.launch {
            localStorageRepository.onDownloadFact(catFact, enabled)
        }
    }
}