package com.juliuscanute.tkural.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliuscanute.tkural.repository.ThirukuralRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThirukuralViewModel @Inject constructor(
    private val kuralRepository: ThirukuralRepository,
    private val kuralViewStateMapper: KuralViewStateMapper
) : ViewModel() {
    val kuralViewState: MutableStateFlow<ThirukuralViewState?> = MutableStateFlow(null)
    val navigationViewState: MutableStateFlow<NavigationViewState?> = MutableStateFlow(null)
    var totalCount: Int = 0

    init {
        viewModelScope.launch {
            totalCount = kuralRepository.getThirukuralCount()
            val kuralNumber = kuralRepository.kuralNumber.first()
            loadKural(kuralNumber)
        }
    }

    fun loadKural(kuralNo: Int) {
        viewModelScope.launch {
            val kuralWithDetails = kuralRepository.getThirukuralWithDetails(kuralNo)
            kuralWithDetails?.let {
                kuralViewState.value = kuralViewStateMapper.mapToViewState(kuralWithDetails)
            }
            kuralRepository.saveKuralNumber(kuralNo)
            updateNavigationState(kuralNo)
        }
    }

    fun loadPreviousKural(kuralNo: Int) {
        viewModelScope.launch {
            loadKural(kuralNo)
            updateNavigationState(kuralNo)
        }
    }

    fun loadNextKural(kuralNo: Int) {
        viewModelScope.launch {
            loadKural(kuralNo)
            updateNavigationState(kuralNo)
        }
    }

    fun updateNavigationState(kuralNo: Int) {
        navigationViewState.value =
            kuralViewStateMapper.mapToNavigationState(this, kuralNo, totalCount)
    }
}