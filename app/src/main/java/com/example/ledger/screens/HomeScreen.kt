package com.example.ledger.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.ledger.R
import java.text.SimpleDateFormat
import java.util.*

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
    var isIncomeState by remember { mutableStateOf(false) }
    var dayTimestampState by remember { mutableStateOf(0L) }
    var timeTimestampState by remember { mutableStateOf(0L) }
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
        MyDatePicker { selectedTimestamp ->
            // 更新选择的日期时间戳
            dayTimestampState = selectedTimestamp
        }
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
                onValueChange = { newAmount ->
                    // 检查输入是否为数字
                    if (newAmount.isEmpty() || newAmount.toDoubleOrNull() != null) {
                        amountState = newAmount
                    }
                },
                label = { Text("金额") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = isIncomeState,
                    onClick = { isIncomeState = !isIncomeState },
                )
                Text(
                    text = "收入",
                    Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { isIncomeState = !isIncomeState }
                )
            }
            Button(
                onClick = {
                    Log.d("Debug", "isIncomeState: $isIncomeState")
                    Log.d("Debug", "dayTimestampState: $dayTimestampState")
                    Log.d("Debug", "timeTimestampState: $timeTimestampState")
                    Log.d("Debug", "categoryState: $categoryState")
                    Log.d("Debug", "tagState: $tagState")
                    Log.d("Debug", "descriptionState: $descriptionState")
                    Log.d("Debug", "amountState: $amountState")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .weight(1f)
                    .padding(vertical = 8.dp)
            ) {
                Text("记他妈的")
            }
        }

        if (dayTimestampState == 0L) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            dayTimestampState = calendar.timeInMillis
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyDatePicker(onDateSelected: (Long) -> Unit) {
    var selectedDate by remember { mutableStateOf<Date?>(Date()) }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Button(
        onClick = {
            isDatePickerVisible = true
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(0.dp)
    ) {
        Text(selectedDate?.toFormattedString() ?: "Select Date")
    }

    // Date Picker Dialog
    if (isDatePickerVisible) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {
                isDatePickerVisible = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedDate = Date(datePickerState.selectedDateMillis ?: 0)
                        isDatePickerVisible = false

                        // 调用回调函数，传递选择的日期的时间戳
                        selectedDate?.let { date ->
                            onDateSelected(date.time)
                        }
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isDatePickerVisible = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun Date.toFormattedString(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}