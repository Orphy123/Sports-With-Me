package com.apiguave.newTins.ui.components

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.apiguave.newTins.domain.profile.entity.UserPicture
import com.apiguave.newTins.ui.theme.*

// Constants
private const val ColumnCount = 3
private const val GridItemCount = 9
const val RowCount = 1 + (GridItemCount -1) / ColumnCount
private val RoundedCornerSize = 6.dp
private val CardPaddingSize = 8.dp
private val IconPaddingSize = 2.dp

@Composable
fun PictureGridRow(
    rowIndex: Int,
    pictures: List<UserPicture>,
    onAddPicture: () -> Unit,
    onAddedPictureClicked: (Int) -> Unit
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ){
        repeat(ColumnCount) { columnIndex ->
            val cellIndex = rowIndex * ColumnCount + columnIndex

            if(cellIndex < pictures.size){
                SelectedPictureItem(
                    imageUri = pictures[cellIndex].uri,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(.6f),
                    onClick = { onAddedPictureClicked(cellIndex) }
                )
            } else {
                EmptyPictureItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(.6f),
                    onClick = onAddPicture
                )
            }
        }
    }
}



@Composable
fun EmptyPictureItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Create a clickable box as the container
    Box(modifier = modifier.clickable(onClick = onClick)) {

        // Use a Card for a modern look, applying the rounded corners
        Card(
            shape = RoundedCornerShape(RoundedCornerSize),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = CardPaddingSize),
            // Fetch the background color from the current theme
            backgroundColor = MaterialTheme.colors.background,
            content = {

                // Define the border color using onSurface color from the current theme
                val borderColor = MaterialTheme.colors.onSurface

                // Draw a dashed border rectangle inside the card
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRoundRect(
                        color = borderColor,
                        style = Stroke(
                            width = 4.dp.toPx(),
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5.dp.toPx(), 5.dp.toPx()), 0f)
                        )
                    )
                }
            }
        )

        // Display an 'Add' icon with a gradient background
        Icon(
            Icons.Filled.Add,
            tint = Color.White,
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colors.secondary,
                            MaterialTheme.colors.primary
                        )
                    ),
                    CircleShape
                )
                .padding(IconPaddingSize)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }
}


@Composable
fun SelectedPictureItem(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    // Create a clickable box as the container
    Box(modifier = modifier.clickable(onClick = onClick)) {

        // Use a Card for a modern look, applying the rounded corners
        Card(
            shape = RoundedCornerShape(RoundedCornerSize),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.6f)
                .padding(all = CardPaddingSize),
            content = {

                // Load and display the image using AsyncImage
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier.fillMaxSize(),
                    onState = {},
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null
                )
            }
        )

        // Display a 'Close' icon with a white circular background
        Icon(
            Icons.Filled.Close,
            // Use the primary color from the theme for the icon tint
            tint = MaterialTheme.colors.primary,
            modifier = Modifier
                .background(color = MaterialTheme.colors.onPrimary, shape = CircleShape)
                .padding(IconPaddingSize)
                .align(Alignment.BottomEnd),
            contentDescription = null
        )
    }
}
