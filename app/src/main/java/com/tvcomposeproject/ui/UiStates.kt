package com.tvcomposeproject.ui

import com.tvcomposeproject.datamodels.DemoScreenData
import com.tvcomposeproject.datamodels.FeedbackScreenData
import com.tvcomposeproject.datamodels.SubmitFeedbackResponse

sealed interface DemoScreenUiState {
    object Loading : DemoScreenUiState
    data class Success(val data: DemoScreenData) : DemoScreenUiState
    data class Error(val message: String) : DemoScreenUiState
}

sealed interface FeedbackScreenUiState {
    object Loading : FeedbackScreenUiState
    data class Success(val data: FeedbackScreenData) : FeedbackScreenUiState
    data class Error(val message: String) : FeedbackScreenUiState
}

// Separate state for submission result if needed
sealed interface FeedbackSubmissionState {
    object Idle : FeedbackSubmissionState
    object Submitting : FeedbackSubmissionState
    data class Success(val response: SubmitFeedbackResponse) : FeedbackSubmissionState
    data class Error(val message: String) : FeedbackSubmissionState
}
