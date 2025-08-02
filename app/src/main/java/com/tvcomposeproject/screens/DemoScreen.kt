package com.tvcomposeproject.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DemoScreen() {
    val localContext = LocalContext.current
    val buttonFocusRequester = remember { FocusRequester() }
    var isButtonFocused by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Demo Screen", style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.White
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                Toast.makeText(localContext, "DemoScreen Clicked", Toast.LENGTH_LONG).show()
            }, modifier = Modifier
                .focusRequester(buttonFocusRequester)
                .background(
                    color = if (isButtonFocused) Color.White else Color.Transparent,
                    shape = RoundedCornerShape(4.dp)
                )
                .onFocusChanged { focusState ->
                    isButtonFocused = focusState.isFocused
                }) {
            Text(
                "Show Demo",
                style = TextStyle(color = if (isButtonFocused) Color.Black else Color.White)
            )
        }
    }
}