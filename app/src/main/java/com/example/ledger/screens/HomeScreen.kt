package com.example.ledger.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ledger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    TopAppBar(
        title = { /* Title goes here */ },
        navigationIcon = {
            // 左上角按钮
            IconButton(
                onClick = {
                    // 处理按钮点击事件，例如触发导航
                },
                modifier = Modifier.padding(12.dp)
            ) {
                // 这里使用的是一个 SVG 图标，你可以替换为你自己的 SVG 图标
                // 这里使用的是一个 SVG 图标，根据系统暗亮模式切换颜色
                val iconPainter = painterResource(R.drawable.settings_24)

                Image(
                    painter = iconPainter,
                    contentDescription = "Setting",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        },
        actions = {
            // 右上角的其他操作按钮可以放在这里
        },
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {

        Text(text = "记账主页", Modifier.padding(top = 80.dp))

        Button(modifier = Modifier.padding(top = 80.dp), onClick = {
            navController.navigate("setting")
        }) {
            Text(text = "跳转到设置")

        }

        Button(modifier = Modifier.padding(top = 80.dp), onClick = {
            navController.navigate("profile")
        }) {
            Text(text = "跳转到详情")

        }
    }

}