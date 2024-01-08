package com.example.ledger.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ledger.R
import com.example.ledger.data.database.Ledger
import com.example.ledger.data.database.LedgerDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

@Composable
fun HomePage(navController: NavController, db: LedgerDatabase) {
    TopAppBar(navController)

    var scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
            .verticalScroll(scrollState)
    ) {
        LedgerInputSection(db)

    }

}


@Composable
private fun LedgerListItem(ledger: Ledger) {
    Column(
    ) {
        ListItem(
            headlineContent = { Text(ledger.category) },
            supportingContent = { Text(ledger.tag) },
            trailingContent = { if (ledger.isIncome) Text(ledger.amount.toString()) else Text("-" + ledger.amount.toString()) },
        )
        HorizontalDivider()
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun LedgerInputSection(db: LedgerDatabase) {
    // 表单字段
    var isIncomeState by remember { mutableStateOf(false) }
    var dayTimestampState by remember { mutableStateOf(getTodayTimeStamp()) }
    var timeTimestampState by remember { mutableStateOf(0L) }
    var categoryState by remember { mutableStateOf("") }
    var tagState by remember { mutableStateOf("") }
    var descriptionState by remember { mutableStateOf("") }
    var amountState by remember { mutableStateOf("") }

    var ledgerListState by remember { mutableStateOf(emptyList<Ledger>()) }
    LaunchedEffect(dayTimestampState) {
        val newLedgerList = withContext(Dispatchers.IO) {
            val ledgerDao = db.ledgerDao()
            val resultList = ledgerDao.getLedgersByDay(dayTimestampState).toList()
            resultList.firstOrNull() ?: emptyList()
        }

        ledgerListState = newLedgerList
    }

    // 获取数据库单例
    val ledgerDao = db.ledgerDao()
    val ledgerList by ledgerDao.getLedgersByDay(dayTimestampState).collectAsState(initial = listOf())

    Log.e("日期组件在变", dayTimestampState.toString())

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
                    LedgerDatabase.launch {
                        try {
                            var ledger = Ledger(
                                null, categoryState, tagState, descriptionState, amountState.toDouble(),
                                isIncomeState, dayTimestampState, timeTimestampState
                            );


                            val ledgerDao = db.ledgerDao()
                            ledgerDao.insertLedger(ledger)
                            Log.d("Debug", "插入成功啊，牢底！")
                            var ledgerList = ledgerDao.getAllLedgers()
                            Log.d("Debug", ledgerList.toString())


                        } catch (e: Exception) {
                            println(e)
                        }
                    }
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
    }

    ledgerList.forEach { ledger ->
        LedgerListItem(ledger)
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

private fun getTodayTimeStamp(): Long {
    // 获取今天的日期
    val today = LocalDate.now()

    // 使用 Asia/Shanghai 时区获取当前时间
    val todayMidnight = ZonedDateTime.of(today.atStartOfDay(), ZoneOffset.UTC)

    val timeStampStr = todayMidnight.toEpochSecond().toString() + "000"

    // 获取今天 0 点的时间戳
    return timeStampStr.toLong()
}


// 顶部按钮导航
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(navController: NavController) {
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
}