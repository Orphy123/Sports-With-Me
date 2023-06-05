package com.apiguave.newTins.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.apiguave.newTins.domain.profilecard.entity.Profile



@Composable
fun ProfileCardView(profile: Profile, modifier: Modifier = Modifier, contentModifier: Modifier = Modifier) {
    var currentIndex by remember { mutableStateOf(0) }
    var showProfileId by remember { mutableStateOf(false) }


    val gradient = Brush.verticalGradient(
        colorStops = arrayOf(
            .65f to Color.Transparent,
            1f to Color.Black.copy(alpha = 0.7f)
        )
    )

    Card(modifier = modifier
        .fillMaxWidth()
        .aspectRatio(.6f)
        .shadow(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(Modifier.fillMaxSize()) {
            // Picture
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = profile.pictures[currentIndex],
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            // Gradient
            Spacer(Modifier.fillMaxSize().background(gradient))

            Box(contentModifier.fillMaxSize()) {
                // Upper picture index indicator
                Row(
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp)
                ) {
                    repeat(profile.pictures.size) { index ->
                        Box(
                            Modifier
                                .weight(1f)
                                .height(3.dp)
                                .padding(horizontal = 4.dp)
                                .alpha(if (index == currentIndex) 1f else .5f)
                                .background(if (index == currentIndex) Color.White else Color.LightGray))
                    }
                }

                // Clickable
                Row(Modifier.fillMaxSize()) {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { if (currentIndex > 0) currentIndex-- })
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .clickable { if (currentIndex < profile.pictures.size - 1) currentIndex++ }
                    )
                }


                // Information
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = profile.name, fontWeight = FontWeight.Bold, color = Color.White, fontSize = 24.sp)
                        Spacer(Modifier.width(8.dp))
                        Text(text = profile.age.toString(), color = Color.White, fontSize = 22.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { showProfileId = !showProfileId },
                            imageVector = Icons.Default.Info,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                    if (showProfileId) {
                        Text(text = "Love to Play both Soccer and Tennis", color = Color.White, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}
