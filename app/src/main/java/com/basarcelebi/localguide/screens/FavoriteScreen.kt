package com.basarcelebi.localguide.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basarcelebi.localguide.viewmodel.FavoritesViewModel

@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: FavoritesViewModel = viewModel()) {
    val favoritePlaces by viewModel.favoritePlaces.observeAsState(emptyList())

    LazyColumn {
        items(favoritePlaces) { place ->
            PlaceCard(place, viewModel::toggleFavorite)
        }
    }
}






@Preview (showBackground = true)
@PreviewScreenSizes
@Composable
fun FavoriteScreenPreview()
{
    val navController = rememberNavController()
    FavoriteScreen(navController)
}