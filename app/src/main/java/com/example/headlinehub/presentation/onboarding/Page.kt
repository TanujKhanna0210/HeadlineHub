package com.example.headlinehub.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.headlinehub.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Welcome to HeadlineHub!",
        description = "Your go-to source for breaking news and personalized updates.\nStay informed with the latest stories, tailored just for you.",
        image = R.drawable.onboarding_1
    ),
    Page(
        title = "Explore Diverse Perspectives",
        description = "Immerse yourself in a variety of topics, from global affairs to niche interests.",
        image = R.drawable.onboarding_2
    ),
    Page(
        title = "Save and Access Anytime",
        description = "Found an article you love? Save it for later!",
        image = R.drawable.onboarding_3
    )
)