package com.example.ledger.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ledger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController) {
    TopAppBar(
        title = { "目前没看到有啥用" },
        navigationIcon = {
            // 左上角按钮
            IconButton(
                onClick = {
                    // 处理按钮点击事件，例如触发导航
                    navController.navigate("profile")
                },
                modifier = Modifier.padding(12.dp)
            ) {
                // 这里使用的是一个 SVG 图标，你可以替换为你自己的 SVG 图标
                // 这里使用的是一个 SVG 图标，根据系统暗亮模式切换颜色
                val iconPainter = painterResource(R.drawable.assessment_24)

                Image(
                    painter = iconPainter,
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        },
        actions = {
            // 右上角的其他操作按钮可以放在这里
            // 左上角按钮
            IconButton(
                onClick = {
                    // 处理按钮点击事件，例如触发导航
                    navController.navigate("setting")
                },
                modifier = Modifier.padding(12.dp)
            ) {
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
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        LedgerInputSection()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LedgerInputSection() {
    // 表单字段
    var categoryState by remember { mutableStateOf("") }
    var tagState by remember { mutableStateOf("") }
    var descriptionState by remember { mutableStateOf("") }
    var amountState by remember { mutableStateOf("") }

    // 获取软键盘控制器
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = categoryState,
                onValueChange = { categoryState = it },
                label = { Text("类目") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = tagState,
                onValueChange = { tagState = it },
                label = { Text("标签") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = descriptionState,
                onValueChange = { descriptionState = it },
                label = { Text("描述") },
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = amountState,
                onValueChange = { amountState = it },
                label = { Text("金额") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // 在这里执行输入完成后的操作
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp)
            )
        }
    }
}