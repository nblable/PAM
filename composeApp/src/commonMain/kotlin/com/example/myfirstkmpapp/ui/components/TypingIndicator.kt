package com.example.myfirstkmpapp.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

@Composable
fun TypingIndicator() {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(3) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "typing")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f, targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600), repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 200)
                ), label = "alpha"
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .alpha(alpha)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
            )
        }
    }
}