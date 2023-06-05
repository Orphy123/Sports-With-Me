package com.apiguave.newTins.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import com.apiguave.newTins.R
import com.apiguave.newTins.extensions.conditional
import com.apiguave.newTins.extensions.withLinearGradient
import com.apiguave.newTins.ui.theme.Orange
import com.apiguave.newTins.ui.theme.Pink

@Composable
fun LoadingView() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = .6f))
            .clickable(enabled = false, onClick = {}),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedGradientLogo()
    }
}

@Composable
fun AnimatedGradientLogo(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Image(
        painterResource(id = R.drawable.tinder_logo),
        contentDescription = "Animated Gradient Logo",
        modifier = modifier
            .fillMaxWidth() // Adjust the size here
            .graphicsLayer {
                rotationZ = angle
            }
    )
}


@Composable
fun AnimatedLogo(modifier: Modifier = Modifier, isAnimating: Boolean){
    val infiniteTransition = rememberInfiniteTransition()
    val animatedLogoScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(animation = tween(1000), repeatMode = RepeatMode.Reverse)
    )
    Image(
        painter = painterResource(id = R.drawable.tinder_logo),
        contentDescription = null,
        modifier = modifier
            .conditional(isAnimating) {
                graphicsLayer(scaleX = animatedLogoScale, scaleY = animatedLogoScale)
            }
    )
}