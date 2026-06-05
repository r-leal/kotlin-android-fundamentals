package com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.CircleSize

enum class MovementDirection {
    RIGHT,
    LEFT,
    DOWN,
    UP
}

fun MovementDirection.getIcon() = when(this) {
    MovementDirection.RIGHT -> Icons.Default.KeyboardArrowRight
    MovementDirection.LEFT -> Icons.Default.KeyboardArrowLeft
    MovementDirection.DOWN -> Icons.Filled.KeyboardArrowDown
    MovementDirection.UP -> Icons.Filled.KeyboardArrowUp
}

fun MovementDirection.onIntent() = when(this) {
    MovementDirection.RIGHT -> FlowCollectIntent.MoveRight(CircleSize)
    MovementDirection.LEFT -> FlowCollectIntent.MoveLeft(CircleSize)
    MovementDirection.DOWN -> FlowCollectIntent.MoveDown(CircleSize)
    MovementDirection.UP -> FlowCollectIntent.MoveUp(CircleSize)
}