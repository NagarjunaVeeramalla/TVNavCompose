package com.tvcomposeproject.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.tvcomposeproject.ui.DemoScreenUiState

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun DemoScreen(demoScreenUiState: DemoScreenUiState) {
    val localContext = LocalContext.current
    val buttonFocusRequester = remember { FocusRequester() }
    var isButtonFocused by remember { mutableStateOf(false) }

    when (demoScreenUiState) {
        is DemoScreenUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is DemoScreenUiState.Success -> {
            val data = demoScreenUiState.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineMedium.copy(color = Color.White),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = data.subtitle,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.LightGray),
                    textAlign = TextAlign.Center
                )
                // You can use an AsyncImage or a similar Coil/Glide composable to load data.imageUrl
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        Toast.makeText(localContext, "Explore Features Clicked!", Toast.LENGTH_LONG).show()
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
                        data.buttonText,
                        style = TextStyle(color = if (isButtonFocused) Color.Black else Color.White)
                    )
                }
            }
        }

        is DemoScreenUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = demoScreenUiState.message,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Red),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}