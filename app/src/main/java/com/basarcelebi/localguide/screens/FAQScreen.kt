package com.basarcelebi.localguide.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.data.FAQItem

@Composable
fun FaqScreen()
{
    val faqs = listOf(
        FAQItem("What is Jetpack Compose?", "Jetpack Compose is a modern toolkit for building native Android UI."),
        FAQItem("How do I use Jetpack Compose?", "You use Jetpack Compose by adding it to your Android project and then using its APIs to build UIs."),
        FAQItem("What is a Composable function?", "A Composable function is a function annotated with @Composable that allows you to define the UI in a declarative way."),
        FAQItem("How do I manage state in Jetpack Compose?", "You manage state in Jetpack Compose using State, MutableState, and various state management tools provided by the library.")
    )

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(faqs) { faq ->
            FAQCard(faq = faq)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FAQCard(faq: FAQItem) {
    var expanded by remember { mutableStateOf(false) }
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val fontSize = with(LocalDensity.current) {
        if (screenWidth < 600.dp) {
            14.sp
        } else if (screenWidth < 840.dp) {
            16.sp
        } else {
            18.sp
        }
    }
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Text component for the question
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier.weight(1f),
                    color = if (isDarkTheme) Color.White else Color.Black,
                    fontSize = fontSize
                )

                // Icon component for expand/collapse
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                )
            }

            // Display the answer when expanded
            if (expanded) {
                Text(
                    text = faq.answer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFAQCard() {
    FAQCard(
        faq = FAQItem("What is Jetpack Compose?", "Jetpack Compose is a modern toolkit for building native Android UI.")
    )
}