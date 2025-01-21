package com.juliuscanute.tkural

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliuscanute.tkural.repository.ThirukuralRepository
import com.juliuscanute.tkural.ui.SearchResultUiState
import com.juliuscanute.tkural.ui.SearchViewStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ThirukuralRepository,
    private val searchViewStateMapper: SearchViewStateMapper
) : ViewModel() {
    val searchViewState = MutableStateFlow<SearchResultUiState>(searchViewStateMapper.mapToInitialState(this))

    fun searchByAll(query: String) {
        viewModelScope.launch {
            val results = repository.searchByAll(query)
            searchViewState.value =
                searchViewStateMapper.mapToUiState(this@SearchViewModel, results)
        }
    }
}