package com.juliuscanute.tkural.ui

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.juliuscanute.tkural.SearchViewModel
import com.juliuscanute.tkural.repository.KuralSuggestion
import javax.inject.Inject

class SearchViewStateMapper @Inject constructor() {

    fun mapToInitialState(viewModel: SearchViewModel): SearchResultUiState {
        return SearchResultUiState(
            onQuery = {
                viewModel.searchByAll(it)
            },
            suggestions = emptyList()
        )
    }

    fun mapToUiState(
        viewModel: SearchViewModel,
        suggestions: List<KuralSuggestion>
    ): SearchResultUiState {
        return SearchResultUiState(
            onQuery = {
                viewModel.searchByAll(it)
            },
            suggestions = suggestions.map {
                SuggestionUiState(
                    kuralNumber = it.kuralNumber,

                    kuralText = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("${it.kuralNumber}. ")
                        }
                        append(it.kural)
                        if (it.context != null) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                                append(" - ${it.context}")
                            }
                        }
                    }
                )
            }
        )
    }
}

data class SearchResultUiState(
    val onQuery: (String) -> Unit,
    val suggestions: List<SuggestionUiState>
)

data class SuggestionUiState(
    val kuralNumber: Int,
    val kuralText: AnnotatedString,
)