package com.tvcomposeproject.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.tvcomposeproject.datamodels.FeedbackQuestion

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun FeedbackScreen() {

    var feedbackQuestions by remember {
        mutableStateOf(
            listOf(
                FeedbackQuestion(
                    question = "Overall Experience",
                    subQuestion = "How would you rate your overall experience with our service?",
                    rating = 0
                ), FeedbackQuestion(
                    question = "Content Quality",
                    subQuestion = "How satisfied are you with the quality of the content?",
                    rating = 0
                ), FeedbackQuestion(
                    question = "Ease of Use",
                    subQuestion = "How easy was it to navigate the app?",
                    rating = 0
                )
            )
        )
    }
    val firstFocusRequester = remember { FocusRequester() }
    val isButtonEnabled = feedbackQuestions.all { it.rating > 0 }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        items(feedbackQuestions.size) { index ->
            val question = feedbackQuestions[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = question.question, style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White, fontSize = 20.sp
                    )
                )
                Text(
                    text = question.subQuestion, style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.Gray, fontSize = 16.sp
                    )
                )
                StarRatingBar(
                    currentRating = question.rating, onRatingChanged = { newRating ->
                        feedbackQuestions = feedbackQuestions.toMutableList().apply {
                            this[index] = question.copy(rating = newRating)
                        }
                    }, modifier = Modifier
                        .padding(top = 8.dp)
                        .then(
                            if (index == 0) Modifier.focusRequester(firstFocusRequester)
                            else Modifier
                        )
                )
            }
        }
        item {
            Button(
                enabled = isButtonEnabled,
                onClick = {
                    feedbackQuestions.forEach { question ->
                        Log.d("FeedbackScreen", "${question.question}: ${question.rating} stars")
                    }
                }, modifier = Modifier
                    .padding(top = 16.dp)
            ) {
                Text("Submit Feedback")
            }
        }
    }
}