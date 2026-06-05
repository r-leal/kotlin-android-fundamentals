package com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models

sealed class FlowCollectIntent {
    data class MoveRight(val step: Float) : FlowCollectIntent()
    data class MoveLeft(val step: Float) : FlowCollectIntent()
    data class MoveDown(val step: Float) : FlowCollectIntent()
    data class MoveUp(val step: Float) : FlowCollectIntent()
}