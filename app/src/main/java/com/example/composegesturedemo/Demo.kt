package com.example.composegesturedemo

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.ConsumedData
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

/**
 * 点击手势
 * clickable修饰符，检测对已应用该修饰符的元素的点击
 * pointerInput修饰符，提供点按手势检测器
 */
@Composable
fun Demo1() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        var count by remember {
            mutableStateOf(0)
        }
        Text(text = "$count",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clickable {
                    ++count
                }
                .wrapContentSize()
                .background(Color.LightGray)
                .padding(horizontal = 50.dp, vertical = 40.dp)
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "$count",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { Log.d("pointerInput", "called when the gesture starts") },
                        onDoubleTap = { Log.d("pointerInput", "called on double tap") },
                        onLongPress = { Log.d("pointerInput", "called on long tap") },
                        onTap = { Log.d("pointerInput", "called on tap") }
                    )
                }
                .wrapContentSize()
                .background(Color.LightGray)
                .padding(horizontal = 50.dp, vertical = 40.dp)
        )
    }
}

/**
 * 滚动修饰符
 * verticalScroll & horizontalScroll,在元素内容边界大于最大尺寸约束时滚动元素
 * 借助ScrollState可以更改滚动位置或获取当前状态。如需使用默认参数创建此列表，请使用rememberScrollState
 */
@Composable
fun Demo2() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .size(100.dp)
                .verticalScroll(rememberScrollState())
        ) {
            repeat(10) {
                Text(text = "Item $it", modifier = Modifier.padding(2.dp))
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        val state = rememberScrollState()
        LaunchedEffect(Unit) {
            delay(1000)
            state.animateScrollTo(500)
        }
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .size(100.dp)
                .verticalScroll(state)
        ) {
            repeat(10) {
                Text(text = "Item $it", modifier = Modifier.padding(2.dp))
            }
        }
    }
}

/**
 * 可滚动修饰符
 * scrollable修饰符与滚动修饰符区别在于scrollable可检测滚动手势但不会偏移其内容。
 * 需要ScrollableController才能正常运行。构造ScrollableController时，必须提供一个
 * consumeScrollDelta函数，该函数将在每个滚动步骤(通过手势输入、平滑滚动或滑动)调用，
 * 并且增量以像素为单位。为了确保正确传播事件，必须从此函数返回使用的滚动距离量。
 */
@Composable
fun Demo3() {
    var offset by remember {
        mutableStateOf(0f)
    }
    Box(
        modifier = Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    delta
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = offset.toString())
    }
}

/**
 * 嵌套滚动
 * Compose有自动处理嵌套滚动，先滚动子view，再滚动父view
 */
@Composable
fun Demo4() {
    val gradient = Brush.verticalGradient(
        0f to Color.Gray,
        1000f to Color.White
    )
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Column {
            repeat(8) {
                Box(
                    modifier = Modifier
                        .height(128.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(brush = gradient)
                            .padding(24.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

/**
 * 拖动
 * draggable修饰符和rememberDraggableState
 * 类似scrollable修饰符和rememberScrollableState
 * 但是只能指定一个方向，水平或者竖直
 */
@Composable
fun Demo5() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var offsetX by remember { mutableStateOf(0f) }
        Text(text = "Drag me", modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                orientation = Orientation.Horizontal, state = rememberDraggableState { delta ->
                    offsetX += delta
                }
            ))
    }
}

/**
 * 拖动2
 * 如果需要控制整个拖动手势，考虑改为pointerInput修饰符使用拖动手势检测器
 */
@Composable
fun Demo6() {
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }
        Box(modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .background(Color.Blue)
            .size(50.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            })
    }
}
