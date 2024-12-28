package com.example.passwordpage

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.passwordpage.composables.ApplyButton
import com.example.passwordpage.composables.Header
import com.example.passwordpage.composables.LinearProgressIndicator
import com.example.passwordpage.composables.TextField

val linearProgressIndicatorTrackColor = Color(0xFFDCDBE5)
val enabledButtonColor = Color(0xFF191AE5)
val disabledButtonColor = Color(0xFF888888)
val enabledButtonTextColor = Color(0xFFFFFFFF)
val disabledButtonTextColor = Color(0xFF000000)

@Composable
fun CreatePasswordPage() {

    var password by remember { mutableStateOf("") }
    val onPasswordChange = remember { { newPassword: String -> password = newPassword } }

    var indicatorCurrentProgress by remember { mutableFloatStateOf(0f) }
    val isButtonEnabled = remember { derivedStateOf { indicatorCurrentProgress == 1f } }

    val progress by animateFloatAsState(
        targetValue = indicatorCurrentProgress,
        label = "progress",
        animationSpec = spring(stiffness = 50f)
    )

    val buttonContainerColor by remember(indicatorCurrentProgress) {
        if (indicatorCurrentProgress == 1f) enabledButtonColor else disabledButtonColor
    }.let { targetColor ->
        animateColorAsState(
            targetValue = targetColor,
            label = "button container color",
            animationSpec = spring(stiffness = 40f)
        )
    }

    val buttonTextColor by remember(indicatorCurrentProgress) {
        if (indicatorCurrentProgress == 1f) enabledButtonTextColor else disabledButtonTextColor
    }.let { targetColor ->
        animateColorAsState(
            targetValue = targetColor,
            label = "button text color",
            animationSpec = spring(stiffness = 50f)
        )
    }

    LaunchedEffect(key1 = password) {
        val conditions = listOf(
            password.containsLetters(),
            password.containsNumbers(),
            password.containsSymbols(),
            password.length >= 8
        ).count { it }

        indicatorCurrentProgress = conditions * 0.25f
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Top
    ) {
        Header()
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            password = { password },
            onPasswordChange = onPasswordChange
        )
        Spacer(modifier = Modifier.height(20.dp))

        LinearProgressIndicator(progress = { progress })
        Spacer(modifier = Modifier.height(30.dp))

        ApplyButton(
            isButtonEnabled = isButtonEnabled.value,
            buttonContainerColor = buttonContainerColor,
            buttonTextColor = buttonTextColor
        )
    }
}