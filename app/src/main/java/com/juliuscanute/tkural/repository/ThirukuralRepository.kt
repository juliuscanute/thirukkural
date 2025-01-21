package com.juliuscanute.tkural.repository

import com.juliuscanute.tkural.data.ThirukuralDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThirukuralRepository @Inject constructor(
    private val thirukuralDao: ThirukuralDao
) {
    suspend fun getThirukuralCount() = thirukuralDao.getThirukuralCount()
    suspend fun getThirukuralWithDetails(kuralNo: Int) =
        thirukuralDao.getThirukuralWithDetails(kuralNo)

    suspend fun searchByAll(query: String): List<KuralSuggestion> {
        if (query.isEmpty()) return emptyList()
        val suggestions = mutableListOf<KuralSuggestion>()
        suggestions.addAll(searchByKuralNumber(query))
        suggestions.addAll(searchByWordMeanings(query))
        suggestions.addAll(searchByThemes(query))
        suggestions.addAll(searchByTransliteration(query))
        suggestions.addAll(searchByMeaning(query))
        suggestions.addAll(searchByVerse(query))
        suggestions.addAll(searchByCombinedExplanation(query))
        suggestions.addAll(searchByExplanation(query))
        suggestions.addAll(searchByStoryTitle(query))
        suggestions.addAll(searchByStoryContent(query))
        suggestions.addAll(searchByStoryMoral(query))

        return if (suggestions.isEmpty())
            emptyList()
        else
            suggestions.distinct()
    }

    private suspend fun searchByKuralNumber(query: String): List<KuralSuggestion> {
        val kuralNumber = query.toIntOrNull() ?: return emptyList()
        val kural = thirukuralDao.getThirukuralWithDetails(kuralNumber)
        return kural?.let {  listOf(KuralSuggestion(kuralNumber, kural.thirukural.verse, null)) }
            ?: emptyList()
    }

    private suspend fun searchByWordMeanings(query: String): List<KuralSuggestion> {
        val wordMeanings = thirukuralDao.getWordMeaningsByTerm(query)
        if (wordMeanings.isEmpty()) return emptyList()

        return runCatching {
            wordMeanings.map {
                thirukuralDao.getThirukuralWithDetailsFromId(it.thirukural_id)
            }.map {
                KuralSuggestion(
                    kuralNumber = it.thirukural.kural_number,
                    kural = it.thirukural.verse,
                    context = it.wordMeanings.joinToString(", ") { it.term }
                )
            }
        }.getOrElse { emptyList() }
    }

    private suspend fun searchByThemes(query: String): List<KuralSuggestion> {
        val themes = thirukuralDao.getThemesByTheme(query)
        if (themes.isEmpty()) return emptyList()

        return runCatching {
            themes.map {
                thirukuralDao.getThirukuralWithDetailsFromId(it.thirukural_id)
            }.map {
                KuralSuggestion(
                    kuralNumber = it.thirukural.kural_number,
                    kural = it.thirukural.verse,
                    context = it.themes.joinToString(", ") { it.theme }
                )
            }
        }.getOrElse { emptyList() }
    }

    private suspend fun searchByTransliteration(query: String): List<KuralSuggestion> {
        val wordMeanings = thirukuralDao.getWordMeaningByTransliteration(query)
        if (wordMeanings.isEmpty()) return emptyList()

        return runCatching {
            wordMeanings.map {
                thirukuralDao.getThirukuralWithDetailsFromId(it.thirukural_id)
            }.map {
                KuralSuggestion(
                    kuralNumber = it.thirukural.kural_number,
                    kural = it.thirukural.verse,
                    context = it.wordMeanings.joinToString(", ") { it.transliteration }
                )
            }
        }.getOrElse { emptyList() }
    }

    private suspend fun searchByMeaning(query: String): List<KuralSuggestion> {
        val wordMeanings = thirukuralDao.getWordMeaningByMeaning(query)
        if (wordMeanings.isEmpty()) return emptyList()

        return runCatching {
            wordMeanings.map {
                thirukuralDao.getThirukuralWithDetailsFromId(it.thirukural_id)
            }.map {
                KuralSuggestion(
                    kuralNumber = it.thirukural.kural_number,
                    kural = it.thirukural.verse,
                    context = it.wordMeanings.joinToString(", ") { it.meaning }
                )
            }
        }.getOrElse { emptyList() }
    }

    private suspend fun searchByVerse(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByVerse(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, null)
        }
    }

    private suspend fun searchByCombinedExplanation(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByCombinedExplanation(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, it.combined_explanation)
        }
    }

    private suspend fun searchByExplanation(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByExplanation(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, it.explanation)
        }
    }

    private suspend fun searchByStoryTitle(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByStoryTitle(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, it.story_title)
        }
    }

    private suspend fun searchByStoryContent(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByStoryContent(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, it.story_content)
        }
    }

    private suspend fun searchByStoryMoral(query: String): List<KuralSuggestion> {
        val thirukurals = thirukuralDao.getThirukuralByStoryMoral(query)
        return thirukurals.map {
            KuralSuggestion(it.kural_number, it.verse, it.story_moral)
        }
    }
}

data class KuralSuggestion(
    val kuralNumber: Int,
    val kural: String,
    val context: String?
)