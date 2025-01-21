package com.juliuscanute.tkural.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ThirukuralDao {
    @Query("SELECT COUNT(*) FROM thirukural")
    suspend fun getThirukuralCount(): Int

    // Fetch all Thirukural entries
    @Query("SELECT * FROM thirukural")
    suspend fun getAllThirukurals(): List<Thirukural>

    // Fetch a single Thirukural by ID
    @Query("SELECT * FROM thirukural WHERE id = :id")
    suspend fun getThirukuralById(id: Int): Thirukural?

    // Fetch all Word Meanings for a specific Thirukural ID
    @Query("SELECT * FROM word_meanings WHERE thirukural_id = :thirukuralId ORDER BY position")
    suspend fun getWordMeaningsByThirukuralId(thirukuralId: Int): List<WordMeaning>

    @Query("SELECT * FROM word_meanings WHERE LOWER(term) LIKE LOWER(:term || '%')")
    suspend fun getWordMeaningsByTerm(term: String): List<WordMeaning>

    @Query("SELECT * FROM word_meanings WHERE LOWER(transliteration) LIKE LOWER(:transliteration || '%')")
    suspend fun getWordMeaningByTransliteration(transliteration: String): List<WordMeaning>

    @Query("SELECT * FROM word_meanings WHERE LOWER(meaning) LIKE LOWER(:meaning || '%')")
    suspend fun getWordMeaningByMeaning(meaning: String): List<WordMeaning>

    // Fetch all Themes for a specific Thirukural ID
    @Query("SELECT * FROM themes WHERE thirukural_id = :thirukuralId")
    suspend fun getThemesByThirukuralId(thirukuralId: Int): List<Theme>

    @Query("SELECT * FROM themes WHERE LOWER(theme) LIKE LOWER(:theme || '%')")
    suspend fun getThemesByTheme(theme: String): List<Theme>

    // Fetch all data for a Thirukural, including word meanings and themes
    @Transaction
    @Query("SELECT * FROM thirukural WHERE kural_number = :id")
    suspend fun getThirukuralWithDetails(id: Int): ThirukuralWithDetails?

    @Transaction
    @Query("SELECT * FROM thirukural WHERE id = :id")
    suspend fun getThirukuralWithDetailsFromId(id: Int): ThirukuralWithDetails

    @Query("SELECT * FROM thirukural WHERE LOWER(verse) LIKE LOWER(:verse || '%')")
    suspend fun getThirukuralByVerse(verse: String): List<Thirukural>

    @Query("SELECT * FROM thirukural WHERE LOWER(combined_explanation) LIKE LOWER(:query || '%')")
    suspend fun getThirukuralByCombinedExplanation(query: String): List<Thirukural>

    @Query("SELECT * FROM thirukural WHERE LOWER(explanation) LIKE LOWER(:query || '%')")
    suspend fun getThirukuralByExplanation(query: String): List<Thirukural>

    @Query("SELECT * FROM thirukural WHERE LOWER(story_title) LIKE LOWER(:query || '%')")
    suspend fun getThirukuralByStoryTitle(query: String): List<Thirukural>

    @Query("SELECT * FROM thirukural WHERE LOWER(story_content) LIKE LOWER(:query || '%')")
    suspend fun getThirukuralByStoryContent(query: String): List<Thirukural>

    @Query("SELECT * FROM thirukural WHERE LOWER(story_moral) LIKE LOWER(:query || '%')")
    suspend fun getThirukuralByStoryMoral(query: String): List<Thirukural>
}

