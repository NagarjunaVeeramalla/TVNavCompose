package com.tvcomposeproject.datamodels

sealed class Screens(val title: String) {
    object DemoScreen : Screens("Demo")
    object Feedback : Screens("Feedback")
}
