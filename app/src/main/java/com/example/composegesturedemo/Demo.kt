package com.example.composegesturedemo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
        modifier = Modifier.fillMaxSize()
    ) {

    }
}