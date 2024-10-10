package com.basarcelebi.localguide.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.data.City
import com.basarcelebi.localguide.data.Place
import com.basarcelebi.localguide.model.PlaceObject
import com.basarcelebi.localguide.network.UserAuth
import com.basarcelebi.localguide.repositories.PlaceRepository
import com.basarcelebi.localguide.viewmodel.FavoritesViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SlideableCityCard(cities: List<City>) {

    BoxWithConstraints {
        val maxWidth = constraints.maxWidth

        Card(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = { /*TODO*/ }
        ) {
            AutoSlidingCarousel(
                items = cities
            ) { index, city ->
                val poppins = FontFamily(
                    Font(R.font.poppins_bold, FontWeight.Bold)
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CityCardContent(city = city)
                    Text(
                        text = "${city.name}, ${city.country}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = Color.White,
                            fontFamily = poppins,
                            fontSize = 24.sp,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun CityCardContent(city: City) {
    Box {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(city.imageRes)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(200.dp)
                .blur(radius = 4.dp),
            colorFilter = ColorFilter.tint(Color.LightGray, blendMode = androidx.compose.ui.graphics.BlendMode.Darken)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = 3000,
    pagerState: PagerState = remember { PagerState() },
    items: List<City>,
    itemContent: @Composable (index: Int, city: City) -> Unit,
) {
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % items.size)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        HorizontalPager(count = items.size, state = pagerState) { page ->
            itemContent(page, items[page])
        }

        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = items.size,
                selectedIndex = if (isDragged) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int,
    selectedIndex: Int,
    dotSize: Dp = 8.dp,
    dotColor: Color = Color.White,
    selectedDotColor: Color = Color.Gray
) {
    Row(modifier = modifier) {
        for (i in 0 until totalDots) {
            val color = if (i == selectedIndex) selectedDotColor else dotColor
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .background(color, shape = CircleShape)
            )
            if (i < totalDots - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}


@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}



@Composable
fun CitiesCard() {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(modifier = Modifier.clickable(onClick = { /*TODO*/ })) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://dynamic-media-cdn.tripadvisor.com/media/photo-o/2b/28/4a/61/caption.jpg?w=1200&h=-1&s=1")
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(75.dp)
                    .blur(radius = 4.dp),
                colorFilter = ColorFilter.tint(Color.LightGray, blendMode = androidx.compose.ui.graphics.BlendMode.Darken)
            )
            val poppins = FontFamily(Font(R.font.poppins_bold))
            Column(modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)) {
                Text(
                    text = "Locations",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.White,
                        fontFamily = poppins,
                        fontSize = 24.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    )
                )
            }
        }
    }

}



@Composable
fun HomeScreen(auth: UserAuth = UserAuth(), viewModel: FavoritesViewModel = viewModel(), navHostController: NavHostController) {
    val cities = listOf(
        City("Istanbul", "Türkiye", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"),
        City("Paris", "France", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"),
        City("New York", "USA", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
    )
    val places = viewModel.places.value ?: emptyList()
    val username = auth.user?.displayName ?: "User"
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            Text(
                text = "Hi, $username! \uD83D\uDC4B",
                style = MaterialTheme.typography.headlineMedium,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Popular Cities",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
            SlideableCityCard(cities = cities)
            CitiesCard()
        }

        item {
            Text(
                text = "Near to you",
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )
        }

        item {
            val categories = listOf("Category 1", "Category 2", "Category 3")
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                items(categories.size) { index ->
                    CategoryCard(
                        category = categories[index],
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selected ->
                            selectedCategory = if (selectedCategory == selected) null else selected
                        }
                    )
                }
            }
        }

        items(places) { place ->
            PlaceCard(navController = navHostController, place, viewModel::toggleFavorite)
        }
    }
}


@Composable
fun CategoryCard(
    category: String,
    selectedCategory: String?,
    onCategorySelected: (String) -> Unit
) {
    val isSelected = selectedCategory == category

    Card(
        modifier = Modifier.padding(end = 8.dp),
        shape = CircleShape,
        onClick = { onCategorySelected(category) }
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isSelected) {
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                modifier = Modifier.align(Alignment.Center),
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}

@Composable
fun PlaceCard(navController: NavHostController = rememberNavController(),place : Place, onFavoriteClick: (Place) -> Unit) {
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("details/${place.name}") }
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(16.dp))
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .size(125.dp)) {
            Box{
                Image(
                    painter = rememberAsyncImagePainter(model = place.imageUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                // Favorite button
                IconButton(
                    onClick = { onFavoriteClick(place) },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(
                            color = Color.White,
                            shape = CircleShape
                        )
                ) {
                    if (place.isFavorited) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.Red
                        )
                    }
                    else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.Black)
                    }

                }

            }


        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Text(
                    text = place.address,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black
                )


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = place.rating.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = if (isDarkTheme) Color.White else Color.Black
                )
            }
            }


    }
}

@Preview
@Composable
fun CategoryCardPreview() {
    CategoryCard("Category 1", "Category 1") {}
}

@Preview
@Composable
fun PlaceCardPreview() {
    val navController = rememberNavController()

    var places by remember { mutableStateOf<List<Place>>(emptyList()) }

    if (places.isNotEmpty()) {
        PlaceCard(navController, places[0]) {}
    } else {
        Text("Loading places...")
    }
}


@Preview
@Composable
fun IndicatorDotPreview() {
    IndicatorDot(size = 16.dp, color = Color.White)
}

@Preview
@Composable
fun SlideableCityCardPreview() {
    val cities = listOf(
        City("Istanbul", "Türkiye", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"),
        City("Paris", "France","https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg"),
        City("New York", "USA", "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__340.jpg")
    )
    SlideableCityCard(cities)
}

@Preview
@Composable
fun CitiesCardPreview() {
    CitiesCard()
}

@Preview (showBackground = true)
@PreviewScreenSizes
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(UserAuth(),FavoritesViewModel(),navController)
}