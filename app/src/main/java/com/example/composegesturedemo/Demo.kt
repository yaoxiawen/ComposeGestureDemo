package com.example.composegesturedemo

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

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