package com.tvcomposeproject.datamodels

data class Question(
    val id: Int,
    var rating: Int, // Made rating mutable as it's likely to change in UI
    val mainQuestion: String,
    val subQuestion: String
)

data class SurveyFeedback(
    val questions: List<Question>
)

data class FeedbackScreenData(
    val title: String,
    var isFeedbackComplete: Boolean, // Made mutable
    val surveyFeedback: SurveyFeedback
)
