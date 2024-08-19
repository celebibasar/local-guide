package com.basarcelebi.localguide.screens

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.basarcelebi.localguide.R
import com.basarcelebi.localguide.data.Place
import com.basarcelebi.localguide.data.User
import com.basarcelebi.localguide.model.PlaceObject

@Composable
fun DetailsScreen(navController: NavHostController, place: Place) {
    val poppins = FontFamily(Font(R.font.poppins))
    val isDarkTheme = isSystemInDarkTheme()
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val isRotated = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            DetailsCard(place = place)
        }
        item{
            Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "\uD83D\uDCAC Reviews",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(start=16.dp))
                ReviewButton()

            }

            ReviewCard(user = User(1, "Basar Celebi", "Izmir","Turkey","basarcelebi","123", R.drawable.logo))
        }
    }

}

@Composable
fun DetailsCard(place : Place) {
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
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            // Image of the place
            Image(painter = rememberAsyncImagePainter(model = place.imageUrl), contentDescription = "Image of the place",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .shadow(1.dp)
                    .height(if (isRotated) screenHeight / 3 else screenWidth / 2),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop)
            // Name of the place and rating
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                if(place.category == "Cafe") {
                    Icon(imageVector = rememberCoffee(), contentDescription = "Coffee Icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp),
                        tint = if(isDarkTheme) Color.White else Color.Black)
                }
                else if (place.category == "Museum") {
                    Icon(imageVector = rememberMuseum(), contentDescription = "Museum Icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp),
                        tint = if(isDarkTheme) Color.White else Color.Black)
                }
                else if (place.category == "Tourist Place") {
                    Icon(imageVector = rememberAttractions(), contentDescription = "Tourist Place Icon",
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(30.dp),
                        tint = if(isDarkTheme) Color.White else Color.Black)
                }
                Text(text = place.name,
                    style = if(isRotated) MaterialTheme.typography.headlineLarge.copy(fontFamily = FontFamily(Font(R.font.poppins_bold))) else MaterialTheme.typography.headlineMedium.copy(fontFamily = FontFamily(Font(R.font.poppins_bold))),
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = if(isRotated) 24.sp else 20.sp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = place.rating.toString(),
                        style = if(isRotated) MaterialTheme.typography.headlineLarge.copy(fontFamily = FontFamily(Font(R.font.poppins_bold))) else MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily(Font(R.font.poppins_bold))),
                        modifier = Modifier.align(Alignment.CenterVertically),
                        fontSize = if(isRotated) 24.sp else 20.sp,
                        color = if(isDarkTheme) Color.White else Color.Black
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(if (isRotated) 24.dp else 20.dp)
                            .align(Alignment.CenterVertically),
                        tint = if(isDarkTheme) Color.White else Color.Black
                    )
                }
            }

            // Description of the place
            Text(text = place.description,
                style = if(isRotated) MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily(Font(R.font.poppins))) else MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily(Font(R.font.poppins))),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                textAlign = if(isRotated) androidx.compose.ui.text.style.TextAlign.Justify else androidx.compose.ui.text.style.TextAlign.Justify,
                fontSize = fontSize,
                color = if(isDarkTheme) Color.White else Color.Black)

            NavigationIcons()

        }
    }
}

@Composable
fun NavigationIcons(context : Context = LocalContext.current, locationQuery: String = "Can Codina, Barcelona")
{
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

    val iconSize = with(LocalDensity.current) {
        if (screenWidth < 600.dp) {
            30.dp
        } else if (screenWidth < 840.dp) {
            40.dp
        } else {
            60.dp
        }
    }
    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically) {
       //Open in Map Button
        Button(modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Unspecified)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = if(isDarkTheme) Color.White else Color.Black,
            ),
            border = if(isDarkTheme) BorderStroke(1.dp, Color.White) else BorderStroke(1.dp, Color.Black),
            onClick = {
                // Tüm harita uygulamalarında çalışacak bir URI oluşturun
                val uri = Uri.parse("geo:0,0?q=${Uri.encode(locationQuery)}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)
            }) {
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location Icon",
                modifier = Modifier.size(iconSize),
                tint = if(isDarkTheme) Color.White else Color.Black)
            Text(text = "Open in Maps",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = poppins,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = fontSize,
                color = if(isDarkTheme) Color.White else Color.Black)
        }


    }

}
@Composable
fun ReviewButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isExpanded by interactionSource.collectIsPressedAsState()

    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val buttonWidth by animateDpAsState(
        targetValue = if (isExpanded) 150.dp else 48.dp
    )



    Button(
        onClick = {showDialog = true},
        modifier = Modifier
            .width(buttonWidth)
            .height(42.dp)
            .padding(end = 16.dp),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp),
        interactionSource = interactionSource,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
        ),
        border = BorderStroke(1.dp, if (isSystemInDarkTheme()) Color.White else Color.Black)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isExpanded) {
                Text(
                    text = "Add Review",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Review",
                modifier = Modifier.padding(start = if (isExpanded) 8.dp else 0.dp,end = if (isExpanded) 8.dp else 0.dp)
            )
        }
    }
    if (showDialog) {
        ReviewDialog(
            onDismiss = { showDialog = false },
            onSubmit = { reviewText, rating ->
                // Burada reviewText ve rating'i işleyin
                showDialog = false
                // Örneğin, bu veriyi bir sunucuya gönderin veya yerel olarak saklayın
                Toast.makeText(context, "Review Submitted!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun ReviewDialog(onDismiss: () -> Unit, onSubmit: (String, Int) -> Unit) {
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }
    var hoveredIndex by remember { mutableStateOf(-1) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add Your Review",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    label = { Text("Your review") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Rating", style = MaterialTheme.typography.bodyLarge)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { index ->
                        val isHovered = index == hoveredIndex
                        val scale by animateFloatAsState(targetValue = if (isHovered) 1.2f else 1.0f)
                        val tint = if (index < rating) Color(0xFFFF9800) else Color.Gray
                        val icon = if (index < rating) Icons.Rounded.Star else Icons.Outlined.Star

                        Icon(
                            imageVector = icon,
                            contentDescription = "Star",
                            tint = tint,
                            modifier = Modifier
                                .size(32.dp)
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            rating = index + 1
                                        },
                                        onPress = { offset ->
                                            hoveredIndex = index
                                            tryAwaitRelease()
                                            hoveredIndex = -1
                                        }
                                    )
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { onSubmit(reviewText, rating) }) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}




@Composable
fun ReviewCard(user : User)
{
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
    Card(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, top = 4.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start) {

                Image(painter = painterResource(id = user.profileImageId),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = androidx.compose.ui.layout.ContentScale.Fit)
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp),verticalArrangement = Arrangement.Center) {
                    Text(text = user.name,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        ),
                        fontSize = fontSize,
                        color = if(isDarkTheme) Color.White else Color.Black)
                    Text(text = "Local Guide - 210 Contributions",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = poppins,
                            fontWeight = FontWeight.Normal
                        ),
                        fontSize = 12.sp,
                        color = if(isDarkTheme) Color.White else Color.Black)
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start) {
                Text(text = "⭐️⭐️⭐️⭐️⭐️",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal
                    ),
                    fontSize = fontSize)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "2 weeks ago",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = poppins,
                        fontWeight = FontWeight.Normal
                    ),
                    fontSize = 12.sp,
                    color = if(isDarkTheme) Color.White else Color.Black)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Start) {
                ExpandableText(text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non diam libero sit amet quam egestas tincidunt. Nullam nec nunc nec nunc ultricies ultricies. Integer nec odio nec odio ultricies ultricies. Integer nec odio nec odio ultricies ultricies. Integer nec odio nec odio ultricies ultricies. Integer nec odio nec odio ultricies ultricies.",
                    fontSize = fontSize,
                    modifier = Modifier.padding(3.dp))


            }

        }



    }
}

@Preview(showBackground = true)
@Composable
fun ReviewCardPreview()
{
    val user = User(1, "Basar Celebi", "Izmir","Turkey","basarcelebi","123", R.drawable.logo)
    ReviewCard(user)
}

@Preview(showBackground = true)
@Composable
fun NavigationIconsPreview()
{
    NavigationIcons()
}

@Preview(showBackground = true)
@Composable
fun DetailsCardPreview() {
    val places = PlaceObject.getPlaces()
    DetailsCard(places[1])
}

@Preview(showBackground = true)
@PreviewScreenSizes
@Composable
fun DetailsScreenPreview() {
    val navController = rememberNavController()
    val place = PlaceObject.getPlaces()[0]
    DetailsScreen(navController, place)
}


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontStyle: FontStyle? = null,
    text: String,
    collapsedMaxLine: Int = 3,
    showMoreText: String = "... Show More",
    showMoreStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.W500),
    showLessText: String = " Show Less",
    showLessStyle: SpanStyle = showMoreStyle,
    textAlign: TextAlign? = null,
    fontSize: TextUnit
) {
    // State variables to track the expanded state, clickable state, and last character index.
    var isExpanded by remember { mutableStateOf(false) }
    var clickable by remember { mutableStateOf(false) }
    var lastCharIndex by remember { mutableStateOf(0) }
    val isDarkTheme = isSystemInDarkTheme()

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .clickable(clickable) {
                isExpanded = !isExpanded
            }
            .then(modifier)
            .fillMaxHeight()
        ) {
            // Text composable with buildAnnotatedString to handle "Show More" and "Show Less" buttons.
            Text(
                modifier = textModifier
                    .fillMaxWidth()
                    .animateContentSize(),
                color = if(isDarkTheme) Color.White else Color.Black,
                text = buildAnnotatedString {
                    if (clickable) {
                        if (isExpanded) {
                            // Display the full text and "Show Less" button when expanded.
                            append(text)
                            withStyle(style = showLessStyle) { append(showLessText) }
                        } else {
                            // Display truncated text and "Show More" button when collapsed.
                            val adjustText = text.substring(startIndex = 0, endIndex = lastCharIndex)
                                .dropLast(showMoreText.length)
                                .dropLastWhile { Character.isWhitespace(it) || it == '.' }
                            append(adjustText)
                            withStyle(style = showMoreStyle) { append(showMoreText) }
                        }
                    } else {
                        // Display the full text when not clickable.
                        append(text)
                    }
                },
                // Set max lines based on the expanded state.
                maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLine,
                fontStyle = fontStyle,
                // Callback to determine visual overflow and enable click ability.
                onTextLayout = { textLayoutResult ->
                    if (!isExpanded && textLayoutResult.hasVisualOverflow) {
                        clickable = true
                        lastCharIndex = textLayoutResult.getLineEnd(collapsedMaxLine - 1)
                    }
                },
                style = style,
                textAlign = textAlign,
                fontSize = fontSize
            )
        }

    }
    // Box composable containing the Text composable.

}



@Composable
fun rememberCoffee(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "coffee",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(18.417f, 29.75f)
                quadToRelative(-4.709f, 0f, -8.105f, -3.333f)
                quadToRelative(-3.395f, -3.334f, -3.395f, -7.959f)
                verticalLineTo(7.875f)
                quadToRelative(0f, -1.042f, 0.791f, -1.833f)
                quadToRelative(0.792f, -0.792f, 1.834f, -0.792f)
                horizontalLineToRelative(21.333f)
                quadToRelative(2.25f, 0f, 3.854f, 1.562f)
                quadToRelative(1.604f, 1.563f, 1.604f, 3.813f)
                reflectiveQuadToRelative(-1.604f, 3.792f)
                quadToRelative(-1.604f, 1.541f, -3.854f, 1.541f)
                horizontalLineToRelative(-0.958f)
                verticalLineToRelative(2.5f)
                quadToRelative(0f, 4.625f, -3.396f, 7.959f)
                quadToRelative(-3.396f, 3.333f, -8.104f, 3.333f)
                close()
                moveTo(9.542f, 13.333f)
                horizontalLineToRelative(17.75f)
                verticalLineTo(7.875f)
                horizontalLineTo(9.542f)
                close()
                moveToRelative(8.875f, 13.792f)
                quadToRelative(3.666f, 0f, 6.271f, -2.563f)
                quadToRelative(2.604f, -2.562f, 2.604f, -6.104f)
                verticalLineToRelative(-2.5f)
                horizontalLineTo(9.542f)
                verticalLineToRelative(2.5f)
                quadToRelative(0f, 3.542f, 2.604f, 6.104f)
                quadToRelative(2.604f, 2.563f, 6.271f, 2.563f)
                close()
                moveToRelative(11.5f, -13.792f)
                horizontalLineToRelative(0.958f)
                quadToRelative(1.167f, 0f, 2f, -0.791f)
                quadToRelative(0.833f, -0.792f, 0.833f, -1.917f)
                quadToRelative(0f, -1.167f, -0.833f, -1.958f)
                quadToRelative(-0.833f, -0.792f, -2f, -0.792f)
                horizontalLineToRelative(-0.958f)
                close()
                moveTo(8.25f, 34.75f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.396f)
                reflectiveQuadToRelative(-0.375f, -0.937f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                reflectiveQuadToRelative(0.958f, -0.375f)
                horizontalLineToRelative(23.333f)
                quadToRelative(0.542f, 0f, 0.917f, 0.375f)
                reflectiveQuadToRelative(0.375f, 0.917f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.917f, 0.375f)
                close()
                moveToRelative(10.167f, -18.792f)
                close()
            }
        }.build()
    }
}
@Composable
fun rememberMuseum(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "museum",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(4.917f, 36.375f)
                quadToRelative(-0.542f, 0f, -0.917f, -0.375f)
                reflectiveQuadToRelative(-0.375f, -0.958f)
                quadToRelative(0f, -0.542f, 0.375f, -0.917f)
                reflectiveQuadToRelative(0.917f, -0.375f)
                horizontalLineTo(7f)
                verticalLineTo(17.375f)
                horizontalLineTo(4.958f)
                quadToRelative(-0.583f, 0f, -0.958f, -0.396f)
                reflectiveQuadTo(3.625f, 16f)
                quadToRelative(0f, -0.375f, 0.187f, -0.729f)
                quadToRelative(0.188f, -0.354f, 0.48f, -0.604f)
                lineToRelative(14.166f, -9.959f)
                quadToRelative(0.375f, -0.291f, 0.75f, -0.395f)
                quadToRelative(0.375f, -0.105f, 0.792f, -0.105f)
                quadToRelative(0.417f, 0f, 0.792f, 0.105f)
                quadToRelative(0.375f, 0.104f, 0.75f, 0.395f)
                lineToRelative(14.166f, 9.959f)
                quadToRelative(0.292f, 0.25f, 0.48f, 0.625f)
                quadToRelative(0.187f, 0.375f, 0.187f, 0.75f)
                quadToRelative(0f, 0.583f, -0.375f, 0.958f)
                reflectiveQuadToRelative(-0.958f, 0.375f)
                horizontalLineToRelative(-2f)
                verticalLineTo(33.75f)
                horizontalLineToRelative(2.041f)
                quadToRelative(0.542f, 0f, 0.917f, 0.396f)
                reflectiveQuadToRelative(0.375f, 0.937f)
                quadToRelative(0f, 0.542f, -0.375f, 0.917f)
                reflectiveQuadToRelative(-0.917f, 0.375f)
                close()
                moveToRelative(4.708f, -2.625f)
                horizontalLineToRelative(20.75f)
                close()
                moveToRelative(5.292f, -3.917f)
                quadToRelative(0.541f, 0f, 0.916f, -0.375f)
                reflectiveQuadToRelative(0.375f, -0.958f)
                verticalLineToRelative(-6f)
                lineToRelative(2.709f, 4.042f)
                quadToRelative(0.208f, 0.333f, 0.5f, 0.458f)
                quadToRelative(0.291f, 0.125f, 0.583f, 0.167f)
                quadToRelative(0.333f, 0f, 0.604f, -0.146f)
                quadToRelative(0.271f, -0.146f, 0.479f, -0.438f)
                lineToRelative(2.709f, -4.083f)
                verticalLineToRelative(6f)
                quadToRelative(0f, 0.583f, 0.396f, 0.958f)
                quadToRelative(0.395f, 0.375f, 0.937f, 0.375f)
                reflectiveQuadToRelative(0.937f, -0.375f)
                quadToRelative(0.396f, -0.375f, 0.396f, -0.958f)
                verticalLineToRelative(-8.292f)
                quadToRelative(0f, -0.708f, -0.479f, -1.187f)
                quadToRelative(-0.479f, -0.479f, -1.187f, -0.479f)
                quadToRelative(-0.459f, 0f, -0.959f, 0.27f)
                quadToRelative(-0.5f, 0.271f, -0.75f, 0.646f)
                lineTo(20f, 24.083f)
                lineToRelative(-3.042f, -4.625f)
                quadToRelative(-0.291f, -0.375f, -0.791f, -0.646f)
                quadToRelative(-0.5f, -0.27f, -0.959f, -0.27f)
                quadToRelative(-0.666f, 0f, -1.146f, 0.479f)
                quadToRelative(-0.479f, 0.479f, -0.479f, 1.146f)
                verticalLineTo(28.5f)
                quadToRelative(0f, 0.583f, 0.396f, 0.958f)
                reflectiveQuadToRelative(0.938f, 0.375f)
                close()
                moveToRelative(15.458f, 3.917f)
                verticalLineTo(14.167f)
                lineTo(20f, 6.875f)
                lineTo(9.625f, 14.167f)
                verticalLineTo(33.75f)
                close()
            }
        }.build()
    }
}
@Composable
fun rememberAttractions(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "attractions",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11.833f, 36.417f)
                quadToRelative(-0.625f, 0f, -0.937f, -0.5f)
                quadToRelative(-0.313f, -0.5f, -0.063f, -1.084f)
                lineToRelative(1.417f, -3.291f)
                quadToRelative(-0.667f, -0.5f, -1.208f, -0.959f)
                quadToRelative(-0.542f, -0.458f, -1.125f, -1.041f)
                quadToRelative(-0.334f, 0.166f, -0.688f, 0.229f)
                quadToRelative(-0.354f, 0.062f, -0.687f, 0.062f)
                quadToRelative(-1.334f, 0f, -2.271f, -0.916f)
                quadToRelative(-0.938f, -0.917f, -0.938f, -2.25f)
                quadToRelative(0f, -0.792f, 0.375f, -1.5f)
                quadToRelative(0.375f, -0.709f, 0.959f, -1.084f)
                quadToRelative(-0.292f, -1f, -0.438f, -2.021f)
                quadToRelative(-0.146f, -1.02f, -0.146f, -2.104f)
                quadToRelative(0f, -1.083f, 0.125f, -2.083f)
                quadToRelative(0.125f, -1f, 0.459f, -2f)
                quadToRelative(-0.584f, -0.458f, -0.959f, -1.104f)
                quadToRelative(-0.375f, -0.646f, -0.375f, -1.479f)
                quadToRelative(0f, -1.334f, 0.938f, -2.271f)
                quadToRelative(0.937f, -0.938f, 2.271f, -0.938f)
                quadToRelative(0.333f, 0f, 0.687f, 0.084f)
                quadToRelative(0.354f, 0.083f, 0.688f, 0.208f)
                quadToRelative(1.375f, -1.458f, 3.104f, -2.437f)
                quadToRelative(1.729f, -0.98f, 3.812f, -1.521f)
                quadToRelative(0.209f, -1.292f, 1.105f, -2.063f)
                quadToRelative(0.895f, -0.771f, 2.062f, -0.771f)
                quadToRelative(1.208f, 0f, 2.125f, 0.792f)
                reflectiveQuadToRelative(1.083f, 2.042f)
                quadToRelative(2.084f, 0.541f, 3.896f, 1.437f)
                quadToRelative(1.813f, 0.896f, 3.188f, 2.438f)
                quadToRelative(0.291f, -0.125f, 0.583f, -0.167f)
                quadToRelative(0.292f, -0.042f, 0.583f, -0.042f)
                quadToRelative(1.334f, 0f, 2.271f, 0.917f)
                quadToRelative(0.938f, 0.917f, 0.938f, 2.25f)
                quadToRelative(0f, 0.875f, -0.375f, 1.521f)
                quadToRelative(-0.375f, 0.646f, -0.959f, 1.062f)
                quadToRelative(0.334f, 1.042f, 0.479f, 2.042f)
                quadToRelative(0.146f, 1f, 0.146f, 2.083f)
                quadToRelative(0f, 1.084f, -0.146f, 2.104f)
                quadToRelative(-0.145f, 1.021f, -0.479f, 2.021f)
                quadToRelative(0.667f, 0.459f, 1f, 1.167f)
                quadToRelative(0.334f, 0.708f, 0.334f, 1.417f)
                quadToRelative(0f, 1.333f, -0.938f, 2.25f)
                quadToRelative(-0.937f, 0.916f, -2.271f, 0.916f)
                quadToRelative(-0.333f, 0f, -0.687f, -0.062f)
                quadToRelative(-0.354f, -0.063f, -0.646f, -0.229f)
                quadToRelative(-0.542f, 0.583f, -1.104f, 1.062f)
                quadToRelative(-0.563f, 0.479f, -1.188f, 0.938f)
                lineToRelative(1.417f, 3.291f)
                quadToRelative(0.25f, 0.584f, -0.083f, 1.084f)
                quadToRelative(-0.334f, 0.5f, -1f, 0.5f)
                quadToRelative(-0.334f, 0f, -0.605f, -0.167f)
                quadToRelative(-0.27f, -0.167f, -0.437f, -0.5f)
                lineToRelative(-1.375f, -3.042f)
                quadToRelative(-0.667f, 0.25f, -1.271f, 0.438f)
                quadToRelative(-0.604f, 0.187f, -1.271f, 0.396f)
                quadToRelative(-0.208f, 1.291f, -1.104f, 2.062f)
                quadToRelative(-0.896f, 0.771f, -2.104f, 0.771f)
                quadToRelative(-1.167f, 0f, -2.062f, -0.771f)
                quadToRelative(-0.896f, -0.771f, -1.105f, -2.104f)
                quadToRelative(-0.708f, -0.167f, -1.312f, -0.354f)
                quadToRelative(-0.604f, -0.188f, -1.188f, -0.479f)
                lineToRelative(-1.458f, 3.083f)
                quadToRelative(-0.167f, 0.333f, -0.437f, 0.5f)
                quadToRelative(-0.271f, 0.167f, -0.605f, 0.167f)
                close()
                moveToRelative(1.334f, -6.959f)
                lineTo(16f, 23.167f)
                quadToRelative(-0.583f, -0.667f, -0.875f, -1.459f)
                quadToRelative(-0.292f, -0.791f, -0.292f, -1.708f)
                quadToRelative(0f, -2.125f, 1.563f, -3.646f)
                quadToRelative(1.562f, -1.521f, 3.687f, -1.521f)
                reflectiveQuadToRelative(3.605f, 1.521f)
                quadToRelative(1.479f, 1.521f, 1.479f, 3.646f)
                quadToRelative(0f, 0.917f, -0.292f, 1.708f)
                quadToRelative(-0.292f, 0.792f, -0.875f, 1.5f)
                lineToRelative(2.833f, 6.25f)
                quadToRelative(0.459f, -0.333f, 0.896f, -0.687f)
                quadToRelative(0.438f, -0.354f, 0.854f, -0.813f)
                quadToRelative(-0.125f, -0.25f, -0.229f, -0.604f)
                quadToRelative(-0.104f, -0.354f, -0.104f, -0.687f)
                quadToRelative(0f, -1.167f, 0.812f, -2.105f)
                quadToRelative(0.813f, -0.937f, 2.146f, -1.104f)
                quadToRelative(0.292f, -0.833f, 0.417f, -1.687f)
                quadToRelative(0.125f, -0.854f, 0.125f, -1.813f)
                quadToRelative(0f, -0.958f, -0.125f, -1.812f)
                quadToRelative(-0.125f, -0.854f, -0.375f, -1.688f)
                quadToRelative(-1.25f, -0.125f, -2.104f, -1.062f)
                quadToRelative(-0.854f, -0.938f, -0.854f, -2.146f)
                quadToRelative(0f, -0.375f, 0.083f, -0.688f)
                quadToRelative(0.083f, -0.312f, 0.208f, -0.645f)
                quadToRelative(-1.291f, -1.25f, -2.729f, -2.084f)
                quadTo(24.417f, 9f, 22.667f, 8.542f)
                quadToRelative(-0.375f, 0.583f, -1.063f, 1f)
                quadToRelative(-0.687f, 0.416f, -1.604f, 0.416f)
                quadToRelative(-0.875f, 0f, -1.562f, -0.416f)
                quadToRelative(-0.688f, -0.417f, -1.063f, -1f)
                quadToRelative(-1.75f, 0.458f, -3.25f, 1.291f)
                quadToRelative(-1.5f, 0.834f, -2.708f, 2.167f)
                quadToRelative(0.166f, 0.333f, 0.229f, 0.625f)
                quadToRelative(0.062f, 0.292f, 0.062f, 0.667f)
                quadToRelative(0f, 1.333f, -0.854f, 2.187f)
                quadToRelative(-0.854f, 0.854f, -2.021f, 0.979f)
                quadToRelative(-0.291f, 0.834f, -0.437f, 1.688f)
                quadToRelative(-0.146f, 0.854f, -0.146f, 1.812f)
                quadToRelative(0f, 0.959f, 0.146f, 1.813f)
                reflectiveQuadToRelative(0.437f, 1.687f)
                quadToRelative(1.292f, 0.167f, 2.084f, 1.084f)
                quadToRelative(0.791f, 0.916f, 0.791f, 2.125f)
                quadToRelative(0f, 0.375f, -0.062f, 0.687f)
                quadToRelative(-0.063f, 0.313f, -0.188f, 0.563f)
                quadToRelative(0.375f, 0.416f, 0.813f, 0.791f)
                quadToRelative(0.437f, 0.375f, 0.896f, 0.75f)
                close()
                moveToRelative(1.958f, 1.167f)
                quadToRelative(0.5f, 0.208f, 1.063f, 0.417f)
                quadToRelative(0.562f, 0.208f, 1.187f, 0.375f)
                quadToRelative(0.417f, -0.625f, 1.083f, -1.021f)
                quadTo(19.125f, 30f, 20f, 30f)
                quadToRelative(0.917f, 0f, 1.604f, 0.396f)
                quadToRelative(0.688f, 0.396f, 1.063f, 1.021f)
                quadToRelative(0.625f, -0.167f, 1.166f, -0.355f)
                quadToRelative(0.542f, -0.187f, 1f, -0.395f)
                lineToRelative(-2.666f, -6.042f)
                quadToRelative(-0.5f, 0.292f, -1.042f, 0.417f)
                reflectiveQuadTo(20f, 25.167f)
                quadToRelative(-0.625f, 0f, -1.188f, -0.146f)
                quadToRelative(-0.562f, -0.146f, -1.062f, -0.479f)
                close()
                moveTo(20f, 22.542f)
                quadToRelative(1.083f, 0f, 1.812f, -0.75f)
                quadToRelative(0.73f, -0.75f, 0.73f, -1.792f)
                reflectiveQuadToRelative(-0.73f, -1.792f)
                quadToRelative(-0.729f, -0.75f, -1.812f, -0.75f)
                quadToRelative(-1.042f, 0f, -1.771f, 0.75f)
                quadToRelative(-0.729f, 0.75f, -0.729f, 1.792f)
                reflectiveQuadToRelative(0.729f, 1.792f)
                quadToRelative(0.729f, 0.75f, 1.771f, 0.75f)
                close()
                moveTo(20f, 20f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberDirections(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "directions",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16.042f, 19.625f)
                horizontalLineToRelative(7.208f)
                verticalLineToRelative(2.083f)
                quadToRelative(0f, 0.459f, 0.417f, 0.625f)
                quadToRelative(0.416f, 0.167f, 0.708f, -0.125f)
                lineToRelative(2.917f, -2.958f)
                quadToRelative(0.416f, -0.417f, 0.416f, -0.938f)
                quadToRelative(0f, -0.52f, -0.416f, -0.937f)
                lineToRelative(-2.917f, -2.917f)
                quadToRelative(-0.292f, -0.333f, -0.708f, -0.166f)
                quadToRelative(-0.417f, 0.166f, -0.417f, 0.625f)
                verticalLineTo(17f)
                horizontalLineToRelative(-8.542f)
                quadToRelative(-0.541f, 0f, -0.916f, 0.396f)
                reflectiveQuadToRelative(-0.375f, 0.937f)
                verticalLineToRelative(5.292f)
                quadToRelative(0f, 0.542f, 0.375f, 0.917f)
                reflectiveQuadToRelative(0.916f, 0.375f)
                quadToRelative(0.584f, 0f, 0.959f, -0.375f)
                reflectiveQuadToRelative(0.375f, -0.917f)
                close()
                moveTo(20f, 36.458f)
                quadToRelative(-0.5f, 0f, -1f, -0.187f)
                quadToRelative(-0.5f, -0.188f, -0.875f, -0.521f)
                lineTo(4.25f, 21.875f)
                quadTo(3.917f, 21.5f, 3.729f, 21f)
                quadToRelative(-0.187f, -0.5f, -0.187f, -1f)
                reflectiveQuadToRelative(0.187f, -1f)
                quadToRelative(0.188f, -0.5f, 0.521f, -0.875f)
                lineTo(18.125f, 4.25f)
                quadToRelative(0.375f, -0.375f, 0.875f, -0.542f)
                quadToRelative(0.5f, -0.166f, 1f, -0.166f)
                reflectiveQuadToRelative(1f, 0.166f)
                quadToRelative(0.5f, 0.167f, 0.875f, 0.542f)
                lineTo(35.75f, 18.125f)
                quadToRelative(0.375f, 0.375f, 0.542f, 0.875f)
                quadToRelative(0.166f, 0.5f, 0.166f, 1f)
                reflectiveQuadToRelative(-0.166f, 1f)
                quadToRelative(-0.167f, 0.5f, -0.542f, 0.875f)
                lineTo(21.875f, 35.75f)
                quadToRelative(-0.375f, 0.333f, -0.875f, 0.521f)
                quadToRelative(-0.5f, 0.187f, -1f, 0.187f)
                close()
                moveToRelative(-6.917f, -9.541f)
                lineTo(20f, 33.875f)
                lineTo(33.875f, 20f)
                lineTo(20f, 6.125f)
                lineTo(6.125f, 20f)
                close()
                moveTo(20f, 20f)
                close()
            }
        }.build()
    }
}

@Composable
fun rememberPhoneInTalk(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "phone_in_talk",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(33.292f, 19.708f)
                quadToRelative(-0.5f, 0f, -0.896f, -0.354f)
                reflectiveQuadToRelative(-0.479f, -0.896f)
                quadToRelative(-0.542f, -4.125f, -3.5f, -7.062f)
                quadToRelative(-2.959f, -2.938f, -7.084f, -3.521f)
                quadToRelative(-0.541f, -0.083f, -0.895f, -0.479f)
                quadToRelative(-0.355f, -0.396f, -0.355f, -0.896f)
                quadToRelative(0f, -0.583f, 0.396f, -0.979f)
                reflectiveQuadToRelative(0.938f, -0.313f)
                quadToRelative(5.208f, 0.584f, 8.875f, 4.271f)
                quadToRelative(3.666f, 3.688f, 4.291f, 8.896f)
                quadToRelative(0.084f, 0.542f, -0.333f, 0.937f)
                quadToRelative(-0.417f, 0.396f, -0.958f, 0.396f)
                close()
                moveToRelative(-7.042f, 0f)
                quadToRelative(-0.417f, 0f, -0.792f, -0.291f)
                quadToRelative(-0.375f, -0.292f, -0.5f, -0.75f)
                quadToRelative(-0.416f, -1.375f, -1.437f, -2.396f)
                quadToRelative(-1.021f, -1.021f, -2.396f, -1.438f)
                quadToRelative(-0.458f, -0.125f, -0.75f, -0.458f)
                reflectiveQuadToRelative(-0.292f, -0.833f)
                quadToRelative(0f, -0.667f, 0.438f, -1.063f)
                quadToRelative(0.437f, -0.396f, 1.021f, -0.229f)
                quadToRelative(2.25f, 0.5f, 3.875f, 2.125f)
                reflectiveQuadToRelative(2.166f, 3.875f)
                quadToRelative(0.125f, 0.583f, -0.271f, 1.021f)
                quadToRelative(-0.395f, 0.437f, -1.062f, 0.437f)
                close()
                moveToRelative(6.708f, 15f)
                quadToRelative(-5.041f, 0f, -10.02f, -2.416f)
                quadToRelative(-4.98f, -2.417f, -8.896f, -6.354f)
                quadTo(10.125f, 22f, 7.688f, 17.062f)
                quadTo(5.25f, 12.125f, 5.25f, 7.042f)
                quadToRelative(0f, -0.75f, 0.521f, -1.271f)
                quadToRelative(0.521f, -0.521f, 1.271f, -0.521f)
                horizontalLineTo(13f)
                quadToRelative(0.583f, 0f, 1f, 0.375f)
                quadToRelative(0.417f, 0.375f, 0.542f, 1f)
                lineToRelative(1.125f, 5.333f)
                quadToRelative(0.083f, 0.542f, -0.042f, 1.021f)
                quadToRelative(-0.125f, 0.479f, -0.417f, 0.813f)
                lineToRelative(-4.083f, 4.083f)
                quadToRelative(2.167f, 3.583f, 4.937f, 6.354f)
                quadTo(18.833f, 27f, 22.375f, 29f)
                lineToRelative(3.958f, -4.042f)
                quadToRelative(0.375f, -0.375f, 0.875f, -0.562f)
                quadToRelative(0.5f, -0.188f, 1f, -0.063f)
                lineToRelative(5.125f, 1.084f)
                quadToRelative(0.625f, 0.125f, 1.021f, 0.604f)
                quadToRelative(0.396f, 0.479f, 0.396f, 1.104f)
                verticalLineToRelative(5.792f)
                quadToRelative(0f, 0.791f, -0.521f, 1.291f)
                quadToRelative(-0.521f, 0.5f, -1.271f, 0.5f)
                close()
                moveTo(9.833f, 15.458f)
                lineTo(13f, 12.25f)
                lineToRelative(-0.875f, -4.375f)
                horizontalLineTo(7.917f)
                quadToRelative(0.041f, 1.625f, 0.521f, 3.5f)
                quadToRelative(0.479f, 1.875f, 1.395f, 4.083f)
                close()
                moveToRelative(22.292f, 16.584f)
                verticalLineToRelative(-4.167f)
                lineTo(28f, 27f)
                lineToRelative(-3.167f, 3.25f)
                quadToRelative(1.667f, 0.75f, 3.563f, 1.229f)
                quadToRelative(1.896f, 0.479f, 3.729f, 0.563f)
                close()
                moveToRelative(-7.292f, -1.792f)
                close()
                moveToRelative(-15f, -14.792f)
                close()
            }
        }.build()
    }
}



