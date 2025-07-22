package com.juliuscanute.tkural.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(
    tableName = "thirukural"
)
data class Thirukural(
    @PrimaryKey val id: Int?,
    val kural_number: Int,
    val verse: String,
    val combined_explanation: String,
    val explanation: String,
    val story_title: String,
    val story_content: String,
    val story_moral: String,
    val image_path: String?
)

@Entity(
    tableName = "word_meanings",
    foreignKeys = [
        ForeignKey(
            entity = Thirukural::class,
            parentColumns = ["id"],
            childColumns = ["thirukural_id"],
            onDelete = ForeignKey.NO_ACTION // Optional: Deletes word meanings if Thirukural is deleted
        )
    ]
)
data class WordMeaning(
    @PrimaryKey val id: Int?,
    val thirukural_id: Int, // Foreign key pointing to `thirukural.id`
    val position: Int,
    val term: String,
    val transliteration: String,
    val meaning: String
)

@Entity(
    tableName = "themes",
    foreignKeys = [
        ForeignKey(
            entity = Thirukural::class,
            parentColumns = ["id"],
            childColumns = ["thirukural_id"],
            onDelete = ForeignKey.NO_ACTION // Optional: Deletes themes if Thirukural is deleted
        )
    ]
)
data class Theme(
    @PrimaryKey val id: Int?,
    val thirukural_id: Int, // Foreign key pointing to `thirukural.id`
    val theme: String
)

// Relationship data class
data class ThirukuralWithDetails(
    @Embedded val thirukural: Thirukural,
    @Relation(
        parentColumn = "id",
        entityColumn = "thirukural_id"
    )
    val wordMeanings: List<WordMeaning>,
    @Relation(
        parentColumn = "id",
        entityColumn = "thirukural_id"
    )
    val themes: List<Theme>
)