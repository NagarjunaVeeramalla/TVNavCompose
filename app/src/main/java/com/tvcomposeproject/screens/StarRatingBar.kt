package com.tvcomposeproject.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ClickableSurfaceDefaults
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun StarRatingBar(
    currentRating: Int, onRatingChanged: (Int) -> Unit, modifier: Modifier = Modifier
) {
    var isRowFocused by remember { mutableStateOf(false) }
    val firstStarFocusRequester = remember { FocusRequester() }
    LaunchedEffect(isRowFocused) {
        if (isRowFocused) {
            firstStarFocusRequester.requestFocus()
        }
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                isRowFocused = focusState.isFocused
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (1..5).forEach { starIndex ->
            val isSelected = starIndex <= currentRating
            var isStarFocused by remember { mutableStateOf(false) }
            val starModifier = Modifier
                .size(48.dp)
                .onFocusChanged { focusState ->
                    isStarFocused = focusState.isFocused
                }
                .graphicsLayer {
                    scaleX = if (isStarFocused) 1.1f else 1.0f
                    scaleY = if (isStarFocused) 1.1f else 1.0f
                }
            Surface(
                onClick = { onRatingChanged(starIndex) },
                modifier = (if (starIndex == 1) {
                    starModifier.focusRequester(firstStarFocusRequester)
                } else {
                    starModifier
                })
                    .focusable(),
                shape = ClickableSurfaceDefaults.shape(shape = RectangleShape),
                colors = ClickableSurfaceDefaults.colors(
                    containerColor = if (isStarFocused) Color.Gray.copy(alpha = 0.3f) else Color.Transparent,
                    contentColor = if (isSelected) Color.Yellow else Color.Gray,
                    focusedContainerColor = Color.Gray.copy(alpha = 0.3f),
                    focusedContentColor = if (isSelected) Color.Yellow else Color.White
                )
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        imageVector = if (isSelected) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "$starIndex star",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
    }
}