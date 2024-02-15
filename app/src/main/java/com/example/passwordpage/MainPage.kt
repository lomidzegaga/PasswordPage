package com.example.passwordpage

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun MainPage() {

    var currentProgress by remember { mutableFloatStateOf(0f) }
    val progress by animateFloatAsState(
        targetValue = currentProgress,
        label = "",
        animationSpec = spring(stiffness = 50f)
    )

    val buttonContainerColor by animateColorAsState(
        targetValue = if (currentProgress == 1f) Color(0xFF191AE5) else Color.Gray,
        label = "",
        animationSpec = spring(stiffness = 40f)
    )

    val buttonTextColor by animateColorAsState(
        targetValue = if (currentProgress == 1f) Color.White else Color.Black,
        label = "",
        animationSpec = spring(stiffness = 50f)
    )

    val (password, enterPassword) = remember { mutableStateOf("") }
    val context = LocalContext.current

    fun updateProgress(newPassword: String) {
        val containsLetters = newPassword.containsLetters()
        val containsNumbers = newPassword.containsNumbers()
        val containsSymbols = newPassword.containsSymbols()
        val isLengthGreaterThan8 = newPassword.length >= 8

        val changes = listOf(
            containsLetters to password.containsLetters(),
            containsNumbers to password.containsNumbers(),
            containsSymbols to password.containsSymbols(),
            isLengthGreaterThan8 to (password.length >= 8)
        )

        val addedChanges = changes.count { (newVal, oldVal) -> newVal && !oldVal }
        val removedChanges = changes.count { (newVal, oldVal) -> !newVal && oldVal }

        currentProgress += (addedChanges - removedChanges) * 0.25f
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 20.dp, top = 25.dp)
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
        OutlinedTextField(
            value = password,
            onValueChange = {
                updateProgress(it)
                enterPassword.invoke(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            label = { Text(text = "Password") },
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black
            )
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
            enabled = currentProgress == 1f,
            shape = RoundedCornerShape(11.dp),
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