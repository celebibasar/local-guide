package com.basarcelebi.localguide.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen() {
    // Icon and Text for each setting mapped to a card
    val settings = listOf(
        "Display",
        "Privacy Settings",
        "Notifications",
        "Help & Support",
        "About",
    )

    LazyColumn {
        items(settings.size) { item ->
            NavigationCard(text = settings[item],{},)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}