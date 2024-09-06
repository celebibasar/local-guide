package com.basarcelebi.localguide.screens

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.provider.ContactsContract.Profile
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.SignInActivity
import com.basarcelebi.localguide.SignUpActivity
import com.basarcelebi.localguide.network.UserAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

@Composable
fun ProfileDetails(navController: NavHostController) {
    // Icon and Text for each setting mapped to a card
    val settings = listOf(
        "Edit Profile",
        "Change Password",
        "Log Out"
    )

    LazyColumn {
        items(settings.size) { item ->
            NavigationCard(text = settings[item], onClick = {
                if (settings[item] == "Log Out") {
                    UserAuth().logout()
                    if (!UserAuth().isUserSignedIn()) {
                        navigateToSignIn(navController.context)
                    }
                } else {
                    // Diğer kartlara tıklama davranışlarını buraya ekleyin
                }
            })
        }
    }
}

fun navigateToSignIn(context: Context) {
    context.startActivity(Intent(context, SignInActivity::class.java))
}

@Composable
fun NavigationCard(text: String, onClick: () -> Unit) {
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val fontSize = with(LocalDensity.current) {
        when {
            screenWidth < 600.dp -> 14.sp
            screenWidth < 840.dp -> 16.sp
            else -> 18.sp
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = Color.Unspecified
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = fontSize
                ),
                color = if (isDarkTheme) Color.White else Color.Black
            )
        }
    }
}





@Preview (showBackground = true)
@Composable
fun ProfileDetailsPreview()
{
    val navController = rememberNavController()
    ProfileDetails(navController)
}