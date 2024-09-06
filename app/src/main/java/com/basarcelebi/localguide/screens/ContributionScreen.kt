package com.basarcelebi.localguide.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.data.User

@Composable
fun ContributionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Kullanıcı Adı ve Seviye
        UserInfoSection()

        Spacer(modifier = Modifier.height(24.dp))

        // İlerleme Çubuğu
        ProgressSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Rozetler
        BadgesSection()

        Spacer(modifier = Modifier.height(24.dp))

        // Katkılar
        ContributionsSection()
    }
}

@Composable
fun UserInfoSection() {

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

    val user = User(
        id = 1,
        name = "Basar Celebi",
        city = "Izmir",
        country = "Turkey",
        email = "william.henry.harrison@example-pet-store.com",
        password = "password123",
        profileImageId = R.drawable.logo
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Kullanıcı Profil İkonu
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Profile",
            modifier = Modifier
                .size(75.dp)
        )

        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                color = if (isDarkTheme) Color.White else Color.Black,
                fontFamily = poppins,
                fontSize = fontSize,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            Text(
                text = "Local Guide Level 3",
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDarkTheme) Color.White else Color.Black,
                fontFamily = poppins,
                fontSize = fontSize,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
            )
        }
    }
}

@Composable
fun ProgressSection() {
    Column {
        // İlerleme Çubuğu
        LinearProgressIndicator(
            progress = { 134f / 250f },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = Color(0xFFFFA500),
            trackColor = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "134 / 250",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Text(
            text = "As you help other people, you earn points for each contribution & get closer to the next level.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}

@Composable
fun BadgesSection() {
    Column {
        Text(
            text = "Badges",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            BadgeItem("Muhtar")
            BadgeItem("Müdavim")
            BadgeItem("Local")
            BadgeItem("Master Local")
        }
    }
}

@Composable
fun BadgeItem(badgeName: String) {
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.Star, // Rozet simgesi
            contentDescription = null,
            modifier = Modifier.size(48.dp),
            tint = Color.LightGray
        )
        Text(
            text = badgeName,
            style = MaterialTheme.typography.bodySmall,
            color = if (isDarkTheme) Color.White else Color.Black,
            modifier = Modifier.width(80.dp),
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontFamily = poppins,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Normal
        )
    }
}

@Composable
fun ContributionsSection() {
    Column {
        Text(
            text = "Number of Contributions",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            ContributionItem("Reviews", 1, Icons.Default.Create)
            ContributionItem("Ratings", 49, Icons.Default.Star)
            ContributionItem("Photos", 0, Icons.Default.FavoriteBorder)
            ContributionItem("Answers", 69, Icons.Default.PlayArrow)
            ContributionItem("Edits", 1, Icons.Default.Edit)
            ContributionItem("Reported Incorrect", 0, Icons.Default.PlayArrow)
        }
    }
}

@Composable
fun ContributionItem(contributionName: String, count: Int, icon: ImageVector) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFFFA500), // Turuncu renkli ikonlar
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = contributionName,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black,
            modifier = Modifier.weight(1f) // Metni sola yaslamak için
        )
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ContributionScreenPreview() {
    ContributionScreen()
}