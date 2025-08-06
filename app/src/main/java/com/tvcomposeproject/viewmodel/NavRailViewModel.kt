package com.tvcomposeproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tvcomposeproject.datamodels.DemoScreenData
import com.tvcomposeproject.datamodels.FeedbackScreenData
import com.tvcomposeproject.datamodels.Question
import com.tvcomposeproject.datamodels.SubmitFeedbackRequest
import com.tvcomposeproject.datamodels.SubmitFeedbackResponse
import com.tvcomposeproject.datamodels.SurveyFeedback
import com.tvcomposeproject.ui.DemoScreenUiState
import com.tvcomposeproject.ui.FeedbackScreenUiState
import com.tvcomposeproject.ui.FeedbackSubmissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NavRailViewModel : ViewModel() {

    private val _demoScreenUiState = MutableStateFlow<DemoScreenUiState>(DemoScreenUiState.Loading)
    val demoScreenUiState: StateFlow<DemoScreenUiState> = _demoScreenUiState.asStateFlow()

    private val _feedbackScreenUiState = MutableStateFlow<FeedbackScreenUiState>(FeedbackScreenUiState.Loading)
    val feedbackScreenUiState: StateFlow<FeedbackScreenUiState> = _feedbackScreenUiState.asStateFlow()

    private val _feedbackSubmissionState = MutableStateFlow<FeedbackSubmissionState>(FeedbackSubmissionState.Idle)
    val feedbackSubmissionState: StateFlow<FeedbackSubmissionState> = _feedbackSubmissionState.asStateFlow()

    init {
        getDemoScreenData()
        getFeedbackScreenData()
    }

    fun getDemoScreenData() {
        viewModelScope.launch {
            _demoScreenUiState.value = DemoScreenUiState.Loading
            // Simulate network delay
            delay(1000)
            _demoScreenUiState.value = DemoScreenUiState.Success(
                DemoScreenData(
                    title = "Welcome to Demo Screen!",
                    imageUrl = "https://example.com/sample_image.png", // Replace with a real placeholder if you have one
                    buttonText = "Explore Features",
                    subtitle = "Discover what our app can do."
                )
            )
        }
    }

    fun getFeedbackScreenData() {
        viewModelScope.launch {
            _feedbackScreenUiState.value = FeedbackScreenUiState.Loading
            // Simulate network delay
            delay(1000)
            _feedbackScreenUiState.value = FeedbackScreenUiState.Success(
                FeedbackScreenData(
                    title = "Help & Feedback",
                    isFeedbackComplete = false,
                    surveyFeedback = SurveyFeedback(
                        questions = listOf(
                            Question(
                                id = 1,
                                rating = 0,
                                mainQuestion = "Overall Experience",
                                subQuestion = "How would you rate your overall experience?"
                            ),
                            Question(
                                id = 2,
                                rating = 0,
                                mainQuestion = "App Performance",
                                subQuestion = "How satisfied are you with the app's performance?"
                            ),
                            Question(
                                id = 3,
                                rating = 0,
                                mainQuestion = "Ease of Use",
                                subQuestion = "How easy was it to navigate and use the app?"
                            )
                        )
                    )
                )
            )
        }
    }

    fun submitFeedback(title: String, questions: List<Question>) {
        viewModelScope.launch {
            _feedbackSubmissionState.value = FeedbackSubmissionState.Submitting
            val request = SubmitFeedbackRequest(title = title, questions = questions)
            // Simulate network delay
            delay(1500)
            // Simulate a successful submission
            _feedbackSubmissionState.value = FeedbackSubmissionState.Success(
                SubmitFeedbackResponse(
                    message = "Feedback submitted successfully!",
                    code = 200
                )
            )
            // Optionally, update the feedback screen state if needed
            // For example, mark feedback as complete or refresh data
            // For now, we'll just reset the submission state after a while
            delay(3000) // Keep message for 3 seconds
            _feedbackSubmissionState.value = FeedbackSubmissionState.Idle
             // Potentially refresh feedback data or mark as complete
            (_feedbackScreenUiState.value as? FeedbackScreenUiState.Success)?.let { currentState ->
                _feedbackScreenUiState.value = currentState.copy(
                    data = currentState.data.copy(isFeedbackComplete = true)
                )
            }
        }
    }
     fun updateQuestionRating(questionId: Int, newRating: Int) {
        (_feedbackScreenUiState.value as? FeedbackScreenUiState.Success)?.let { currentState ->
            val updatedQuestions = currentState.data.surveyFeedback.questions.map {
                if (it.id == questionId) {
                    it.copy(rating = newRating)
                } else {
                    it
                }
            }
            _feedbackScreenUiState.value = currentState.copy(
                data = currentState.data.copy(
                    surveyFeedback = currentState.data.surveyFeedback.copy(
                        questions = updatedQuestions
                    )
                )
            )
        }
    }
}
