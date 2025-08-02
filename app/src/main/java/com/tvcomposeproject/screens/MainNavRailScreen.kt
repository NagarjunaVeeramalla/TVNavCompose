package com.tvcomposeproject.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.NavigationRail
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.tvcomposeproject.datamodels.Screens
import com.tvcomposeproject.ui.theme.TvComposeSampleProjectTheme


@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainNavRailScreen() {
    var selectedScreen by remember { mutableStateOf<Screens>(Screens.DemoScreen) }
    val firstItemFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxSize()
            .background(color = Color.Black)
    ) {
        NavigationRail(
            modifier = Modifier.width(240.dp), containerColor = Color(0xFF2E2E2E), header = {
                Text("App Help")
            }) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectableNavItem(
                    isSelected = selectedScreen is Screens.DemoScreen,
                    onFocused = { selectedScreen = Screens.DemoScreen },
                    onClick = { focusManager.moveFocus(FocusDirection.Right) },
                    label = { Text("Demo", style = MaterialTheme.typography.labelMedium) },
                    modifier = Modifier.focusRequester(firstItemFocusRequester)
                )
                SelectableNavItem(
                    isSelected = selectedScreen is Screens.Feedback,
                    onFocused = { selectedScreen = Screens.Feedback },
                    onClick = { focusManager.moveFocus(FocusDirection.Right) },
                    label = { Text("Feedback", style = MaterialTheme.typography.labelMedium) })
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(32.dp)
        ) {
            when (selectedScreen) {
                is Screens.DemoScreen -> DemoScreen()
                is Screens.Feedback -> FeedbackScreen()
            }
        }
    }
    LaunchedEffect(Unit) {
        firstItemFocusRequester.requestFocus()
    }
    LaunchedEffect(selectedScreen) {
        Log.d("NavRailItem", "selectedScreen: ${selectedScreen.title}")
    }
}

@Preview
@Composable
fun MainNavRailScreenPreview() {
    TvComposeSampleProjectTheme {
        MainNavRailScreen()
    }
}