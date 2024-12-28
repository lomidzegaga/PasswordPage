package com.example.passwordpage.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.passwordpage.linearProgressIndicatorTrackColor

@Composable
fun LinearProgressIndicator(
    modifier: Modifier = Modifier,
    progress: () -> Float
) {
    LinearProgressIndicator(
        progress = progress(),
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
            .padding(horizontal = 20.dp),
        color = Color.Black,
        strokeCap = StrokeCap.Round,
        trackColor = linearProgressIndicatorTrackColor
    )
}