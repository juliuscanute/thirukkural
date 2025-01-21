package com.juliuscanute.tkural.ui

import com.juliuscanute.tkural.data.ThirukuralWithDetails
import javax.inject.Inject

class KuralViewStateMapper @Inject constructor() {
    fun mapToViewState(kuralWithDetails: ThirukuralWithDetails): ThirukuralViewState {
        return ThirukuralViewState(
            kuralNumber = kuralWithDetails.thirukural.kural_number,
            verse = kuralWithDetails.thirukural.verse,
            wordMeanings = kuralWithDetails.wordMeanings.map { meaning ->
                WordMeaningViewState(
                    term = meaning.term,
                    transliteration = meaning.transliteration,
                    index = meaning.position,
                    meaning = meaning.meaning
                )
            },
            combinedExplanation = kuralWithDetails.thirukural.combined_explanation,
            explanation = kuralWithDetails.thirukural.explanation,
            story = StoryViewState(
                title = kuralWithDetails.thirukural.story_title,
                content = kuralWithDetails.thirukural.story_content,
                moral = kuralWithDetails.thirukural.story_moral
            ),
            theme = kuralWithDetails.themes.map { it.theme }
        )
    }

    fun mapToNavigationState(viewModel: ThirukuralViewModel, kuralNumber: Int, totalCount: Int): NavigationViewState {
        return NavigationViewState(
            next = ButtonViewState(
                enabled = kuralNumber < totalCount,
                onClick = { viewModel.loadNextKural(kuralNumber + 1) }
            ),
            previous = ButtonViewState(
                enabled = kuralNumber > 1,
                onClick = { viewModel.loadPreviousKural(kuralNumber - 1) }
            )
        )
    }
}