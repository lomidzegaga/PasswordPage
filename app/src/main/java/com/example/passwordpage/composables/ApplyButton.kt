package com.example.passwordpage.composables

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ApplyButton(
    modifier: Modifier = Modifier,
    isButtonEnabled: Boolean,
    buttonContainerColor: Color,
    buttonTextColor: Color
) {
    val context = LocalContext.current

    Button(
        onClick = {
            Toast.makeText(context, "You made it!!!", Toast.LENGTH_LONG).show()
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 17.dp),
        enabled = isButtonEnabled,
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