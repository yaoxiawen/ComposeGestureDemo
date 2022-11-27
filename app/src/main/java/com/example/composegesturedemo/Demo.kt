package com.example.composegesturedemo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

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