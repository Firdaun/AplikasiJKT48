package com.example.aplikasijkt48.navbar

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Hamburger() {
    var isOpen by remember { mutableStateOf(false) }
    val topRound by animateFloatAsState(targetValue = if (isOpen) 45f else 0f)
    val bottomRound by animateFloatAsState(targetValue = if (isOpen) -45f else 0f)

    val swipeUp by animateDpAsState(targetValue = if (isOpen) 8.dp else 0.dp)
    val swipeBottom by animateDpAsState(targetValue = if (isOpen) (-8).dp else 0.dp)

    val transparantCenter by animateFloatAsState(targetValue = if (isOpen) 0f else 1f)
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { isOpen = !isOpen  }

),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .offset(y = swipeUp)
                .rotate(topRound)
                .size(width = 24.dp, height = 2.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
        Box(
            modifier = Modifier
                .alpha(transparantCenter)
                .size(width = 24.dp, height = 2.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
        Box(
            modifier = Modifier
                .offset(y = swipeBottom)
                .rotate(bottomRound)
                .size(width = 24.dp, height = 2.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )

    }
}