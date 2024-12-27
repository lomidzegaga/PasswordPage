package com.example.passwordpage

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordpage.composables.TextField

@Composable
fun MainPage() {

    var password by remember { mutableStateOf("") }
    val onPasswordChange = remember { { newPassword: String -> password = newPassword } }

    var indicatorCurrentProgress by remember { mutableFloatStateOf(0f) }
    val isButtonEnabled = remember { derivedStateOf { indicatorCurrentProgress == 1f } }

    val context = LocalContext.current

    val progress by animateFloatAsState(
        targetValue = indicatorCurrentProgress,
        label = "progress",
        animationSpec = spring(stiffness = 50f)
    )

    val buttonContainerColor by remember(indicatorCurrentProgress) {
        derivedStateOf {
            if (indicatorCurrentProgress == 1f) Color(0xFF191AE5) else Color.Gray
        }
    }.let { targetColor ->
        animateColorAsState(
            targetValue = targetColor.value,
            label = "button container color",
            animationSpec = spring(stiffness = 40f)
        )
    }

    val buttonTextColor by animateColorAsState(
        targetValue = if (indicatorCurrentProgress == 1f) Color.White else Color.Black,
        label = "buttonTextColor",
        animationSpec = spring(stiffness = 50f)
    )

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
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = 25.dp
                )
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Create a password",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "Use at least 8 characters with a mix of letters, \n numbers & symbols",
            color = Color.Black,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            password = { password },
            onPasswordChange = onPasswordChange
        )
        Spacer(modifier = Modifier.height(20.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .padding(horizontal = 20.dp),
            color = Color.Black,
            strokeCap = StrokeCap.Round,
            trackColor = Color(0xFFDCDBE5)
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                Toast.makeText(context, "You made it!!!", Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 17.dp),
            enabled = isButtonEnabled.value,
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonContainerColor
            )
        ) {
            Text(
                text = "Next",
                modifier = Modifier.padding(vertical = 5.dp),
                color = buttonTextColor
            )
        }
    }
}