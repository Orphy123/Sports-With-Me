package com.apiguave.newTins.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.apiguave.newTins.extensions.conditional
import com.apiguave.newTins.extensions.withLinearGradient
import com.apiguave.newTins.ui.theme.Orange
import com.apiguave.newTins.ui.theme.Pink

@Composable
fun TopBarIcon(painter: Painter, modifier: Modifier, onClick: (() -> Unit )? = null){
    Icon(
        painter = painter,
        modifier = modifier
            .withLinearGradient(Pink, Orange)
            .conditional(onClick != null) { clickable(onClick = onClick!!) },
        contentDescription = null)

}

@Composable
fun TopBarIcon(@DrawableRes resId: Int, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = painterResource(id = resId), modifier = modifier, onClick = onClick)
}
@Composable
fun TopBarIcon(imageVector: ImageVector, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null){
    TopBarIcon(painter = rememberVectorPainter(image = imageVector), modifier = modifier, onClick = onClick)
}

