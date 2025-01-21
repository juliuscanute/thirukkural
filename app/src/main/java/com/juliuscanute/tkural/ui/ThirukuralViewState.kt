package com.juliuscanute.tkural.ui


data class NavigationViewState(
    val next: ButtonViewState,
    val previous: ButtonViewState
)

data class ButtonViewState(
    val enabled: Boolean,
    val onClick: () -> Unit
)

data class ThirukuralViewState(
    val kuralNumber: Int,
    val verse: String,
    val wordMeanings: List<WordMeaningViewState>,
    val combinedExplanation: String,
    val explanation: String,
    val story: StoryViewState,
    val theme: List<String>
)

data class WordMeaningViewState(
    val term: String,
    val transliteration: String,
    val index: Int,
    val meaning: String
)

data class StoryViewState(
    val title: String,
    val content: String,
    val moral: String
)