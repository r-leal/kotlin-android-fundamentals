package com.rafaelleal.kotlinandroidfundamentals.architectures.views.models

sealed class LoginEffect {
    object NavigateToHome : LoginEffect()
}