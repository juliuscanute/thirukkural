package com.juliuscanute.tkural.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThirukuralScreen(innerPadding: PaddingValues, viewState: ThirukuralViewState) {
    var expandedSection by remember { mutableStateOf<String?>(null) } // Track the expanded section

    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(innerPadding).padding(horizontal = 16.dp)) {
        Text(
            text = "#${viewState.kuralNumber}",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = viewState.verse, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = getDrawableResourceId(viewState.kuralNumber)),
            contentDescription = "Illustration",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        CollapsibleSection(
            title = "Word Meanings",
            isExpanded = expandedSection == "Word Meanings",
            onExpand = { expandedSection = if (expandedSection == "Word Meanings") null else "Word Meanings" }
        ) {
            WordMeaningItem(viewState)
        }

        CollapsibleSection(
            title = "Detailed Explanation",
            isExpanded = expandedSection == "Explanation",
            onExpand = { expandedSection = if (expandedSection == "Explanation") null else "Explanation" }
        ) {
            Column {
                Text(text = viewState.explanation, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = viewState.combinedExplanation,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        CollapsibleSection(
            title = "Story",
            isExpanded = expandedSection == "Story",
            onExpand = { expandedSection = if (expandedSection == "Story") null else "Story" }
        ) {
            Column {
                Text(text = viewState.story.title, style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = viewState.story.content, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Moral", style = MaterialTheme.typography.titleSmall)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = viewState.story.moral,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        CollapsibleSection(
            title = "Themes",
            isExpanded = expandedSection == "Themes",
            onExpand = { expandedSection = if (expandedSection == "Themes") null else "Themes" }
        ) {
            Text(
                text = viewState.theme.joinToString(", "),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun CollapsibleSection(
    title: String,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onExpand() }
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = MaterialTheme.typography.headlineMedium)
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
            if (isExpanded) {
                Box(modifier = Modifier.padding(16.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun WordMeaningItem(meanings: ThirukuralViewState) {
    Column(modifier = Modifier.padding(8.dp)) {
        meanings.wordMeanings.forEach { meaning ->
            Text(
                text = "${meaning.index}. ${meaning.term} (${meaning.transliteration})",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = meaning.meaning, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewThirukuralScreen() {
    val viewState = ThirukuralViewState(
        kuralNumber = 1,
        verse = "அகர முதல எழுத்தெல்லாம் ஆதி\nபகவன் முதற்றே உலகு.",
        wordMeanings = listOf(
            WordMeaningViewState(
                "அகர",
                "Akar",
                1,
                "'Akar' is the first letter in the Tamil alphabet, which is 'அ'."
            ),
            WordMeaningViewState(
                "முதல",
                "Mudhal",
                2,
                "'Mudhal' translates to 'first' or 'beginning'."
            ),
            WordMeaningViewState("உலகு", "Ulagu", 7, "'Ulagu' denotes 'the world'.")
        ),
        combinedExplanation = "Just as 'Akar' is the first of all letters...",
        explanation = "The Kural begins with an analogy...",
        story = StoryViewState(
            title = "The Origin of the Alphabet",
            content = "Once upon a time, in a realm unknown...",
            moral = "The story underlines that everything originated..."
        ),
        theme = listOf("Divinity", "Creation")
    )
    ThirukuralScreen(innerPadding = PaddingValues(48.dp), viewState = viewState)
}

@Composable
fun getDrawableResourceId(kuralNumber: Int): Int {
    val context = LocalContext.current
    val resourceName = "kural$kuralNumber"
    return context.resources.getIdentifier(resourceName, "drawable", context.packageName)
}