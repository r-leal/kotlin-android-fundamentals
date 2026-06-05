package com.rafaelleal.kotlinandroidfundamentals.fundamentals.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.FlowCollectIntent
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.FlowCollectState
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.MovementDirection
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.Position
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.getIcon
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.onIntent
import com.rafaelleal.kotlinandroidfundamentals.ui.theme.KotlinAndroidFundamentalsTheme

class FlowCollectActivity : ComponentActivity() {

    private val viewModel by viewModels<FlowCollectViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KotlinAndroidFundamentalsTheme {
                val state by viewModel.state.collectAsState()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FlowCollectContent(
                        modifier = Modifier.padding(innerPadding),
                        state = state,
                        onIntent = viewModel::onIntent
                    )
                }
            }
        }
    }
}

val CircleSize = 40F
val MovementAreaSize = 400F

@Composable
fun FlowCollectContent(
    modifier: Modifier = Modifier,
    state: FlowCollectState,
    onIntent: (FlowCollectIntent) -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()){
        MovementArea(modifier = Modifier.padding(top = 50.dp)) {
            MovementButtonContainer(
                modifier = Modifier
                    .padding(top = state.position.coordY.dp, start = state.position.coordX.dp),
                position = state.position,
                onIntent = onIntent
            )
        }
    }
}

@Composable
fun MovableCursor(
    modifier: Modifier = Modifier,
    color: Color = Color.Blue
) {
    Box(
        modifier = modifier
            .background(color, shape = CircleShape)
            .size(CircleSize.dp)
    ) {

    }
}

@Composable
fun MovementButtonContainer(
    modifier: Modifier = Modifier,
    position: Position = Position(),
    onIntent: (FlowCollectIntent) -> Unit = {}
) {
    var containerWidth = 2.0F*CircleSize
    var containerHeight = 2.0F*CircleSize
    if (position.coordX >= CircleSize) {
        containerWidth += CircleSize
    }
    if (position.coordY >= 0) {
        containerHeight += CircleSize
    }

    Column (modifier = modifier.size(containerWidth.dp, containerHeight.dp)){
        if (position.coordY >= CircleSize) {
            Row {
                if (position.coordX >= CircleSize) {
                    Box(modifier = Modifier.size(CircleSize.dp))
                }
                MovementButton(direction = MovementDirection.UP, onIntent = onIntent)
                if (position.coordX <= MovementAreaSize - CircleSize) {
                    Box(modifier = Modifier.size(CircleSize.dp))
                }
            }
        }
        Row{
            if (position.coordX >= CircleSize) {
                MovementButton(direction = MovementDirection.LEFT, onIntent = onIntent)
            }
            MovableCursor()
            if (position.coordX <= MovementAreaSize - CircleSize) {
                MovementButton(direction = MovementDirection.RIGHT, onIntent = onIntent)
            }
        }
        if (position.coordY <= MovementAreaSize - CircleSize){
            Row {
                if (position.coordX >= CircleSize) {
                    Box(modifier = Modifier.size(CircleSize.dp))
                }
                MovementButton(direction = MovementDirection.DOWN, onIntent = onIntent)
            }
        }
    }
}

@Composable
fun MovementButton(
    modifier: Modifier = Modifier,
    direction: MovementDirection,
    onIntent: (FlowCollectIntent) -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(color = Color.Gray, shape = CircleShape)
            .size(CircleSize.dp)
    ) {
        IconButton(
            onClick = { onIntent(direction.onIntent()) }) {
            Icon( imageVector = direction.getIcon(), contentDescription = null)
        }
    }
}

@Composable
fun MovementArea(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .size(MovementAreaSize.dp)
            .background(color = Color.Yellow.copy(alpha = 0.5f))
    ){
        content()
    }
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
private fun FlowCollectContentPreview() {
    FlowCollectContent(
        state = FlowCollectState()
    )
}

@Preview(showBackground = true)
@Composable
private fun MovementButtonContainerPreview() {
    MovementButtonContainer()
}
@Preview(showBackground = true)
@Composable
private fun MovementButtonContainerCenterPreview() {
    MovementButtonContainer(position = Position(coordX = 100F, coordY = 100F))
}

@Preview
@Composable
private fun MovementAreaPreview() {
    MovementArea()
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun MovableCursorPreview() {
    MovableCursor()
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun MovementButtonRightPreview() {
    MovementButton(direction = MovementDirection.RIGHT)
}
@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun MovementButtonLeftPreview() {
    MovementButton(direction = MovementDirection.LEFT)
}
@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun MovementButtonDownPreview() {
    MovementButton(direction = MovementDirection.DOWN)
}
@Preview(showSystemUi = false, showBackground = true)
@Composable
private fun MovementButtonUpPreview() {
    MovementButton(direction = MovementDirection.UP)
}