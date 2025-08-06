package com.tvcomposeproject.datamodels

data class SubmitFeedbackRequest(
    val title: String,
    val questions: List<Question> // Reusing Question from FeedbackScreenDataModels
)

data class SubmitFeedbackResponse(
    val message: String,
    val code: Int
)
