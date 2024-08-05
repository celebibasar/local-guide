package com.basarcelebi.localguide.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.data.User


@Composable
fun ProfileScreen(navController: NavHostController) {
    val poppins = FontFamily(Font(R.font.poppins))

    val user = User(
        id = 1,
        name = "Basar Celebi",
        city = "Izmir",
        country = "Turkey",
        email = "william.henry.harrison@example-pet-store.com",
        password = "password123",
        profileImageId = R.drawable.logo
    )

    val navCards = listOf(
        Pair("details", "Your Details"),
        Pair("contributions", "Contributions"),
        Pair("settings", "Settings"),
        Pair("faq", "FAQ")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
        }

        item {
            ProfileCard(user)
        }

        item {
            Text(
                text = "Manage your account",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            )
        }

        items(navCards.size) { rowItems ->
            if (rowItems % 2 == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    NavigationCard(
                        navController = navController,
                        navName = navCards[rowItems].first,
                        name = navCards[rowItems].second,
                        imageId = R.drawable.logo
                    )
                    NavigationCard(
                        navController = navController,
                        navName = navCards[rowItems + 1].first,
                        name = navCards[rowItems + 1].second,
                        imageId = R.drawable.logo
                    )
                }
            }
        }
    }
}



@Composable
fun NavigationCard(navController: NavHostController, navName: String, name: String, imageId: Int) {
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val isRotated = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

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
        modifier = Modifier
            .padding(8.dp)
            .size(width = if (isRotated) screenHeight/1.5f else screenWidth/2.5f , height = if (isRotated) screenWidth/4 else screenHeight/4)
            .clickable { navController.navigate(navName) },
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = imageId),
                contentDescription = "Navigation",
                modifier = Modifier
                    .size(if (screenHeight < 800.dp || isRotated) 100.dp else 125.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize
                ),
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }
    }
}




@Composable
fun ProfileCard(user: User) {
    val poppins = FontFamily(Font(R.font.poppins))
    // Profile name and location
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = user.city + ", " + user.country,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                ),
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
        //Profile image
        Column(modifier = Modifier
            .padding(16.dp)
            .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.End)
            )
        }

    }
}

/*@Composable
fun NavigationCard(navController: NavHostController, navName: String, name: String, imageId: Int) {
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()

    BoxWithConstraints(
        modifier = Modifier
            .padding(8.dp)
            .clickable { navController.navigate(navName) },
        contentAlignment = Alignment.Center
    ) {
        val cardWidth: Dp = maxWidth * 0.45f
        val cardHeight: Dp = cardWidth * 1.2f

        Card(
            modifier = Modifier.size(width = cardWidth, height = cardHeight),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Navigation",
                    modifier = Modifier
                        .size(cardWidth * 0.5f)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Bold,
                        fontSize = if(cardWidth > 200.dp) 20.sp else 16.sp
                    ),
                    color = if (isDarkTheme) Color.White else Color.Black
                )
            }
        }
    }
}*/



@Preview(showBackground = true)
@Composable
fun ProfileCardPreview() {
    val user = User(1, "Basar Celebi", "Izmir", "Turkey", "william.henry.harrison@example-pet-store.com", "password123", R.drawable.logo)
    ProfileCard(user)
}


@Preview(showBackground = true)
@Composable
fun NavigationCardPreview() {
    val navController = rememberNavController()
    NavigationCard(navController, "details","Your Details", R.drawable.logo)
}




@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()

    ProfileScreen(navController)
}