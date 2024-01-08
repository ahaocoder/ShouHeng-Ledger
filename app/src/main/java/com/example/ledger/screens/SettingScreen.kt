package com.example.ledger.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ledger.R
import com.example.ledger.data.database.LedgerDatabase
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage(navController: NavController, db: LedgerDatabase) {
    TopAppBar(
        title = { "目前没看到有啥用" },
        navigationIcon = {
            // 左上角按钮
            IconButton(
                onClick = {
                    // 处理按钮点击事件，例如触发导航
                    navController.popBackStack()
                },
                modifier = Modifier.padding(12.dp)
            ) {
                // 这里使用的是一个 SVG 图标，你可以替换为你自己的 SVG 图标
                // 这里使用的是一个 SVG 图标，根据系统暗亮模式切换颜色
                val iconPainter = painterResource(R.drawable.back_24)

                Image(
                    painter = iconPainter,
                    contentDescription = "back",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        })
    var openDialog by remember { mutableStateOf(false) }
    var openInfoDialog by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Text(text = "设置页", Modifier.padding(top = 80.dp))
        Button(
            modifier = Modifier.padding(top = 80.dp),
            onClick = {
                openDialog = true
            }
        ) {
            Text(text = "清空所有数据")
        }
        Button(
            onClick = {
                openInfoDialog = true
            }
        ) {
            Text(text = "关于")
        }
    }

    if (openDialog) {
        DeleteDataDialog(
            onDismissRequest = {
                // 关闭弹窗
                openDialog = false
            },
            onConfirmClick = {
                // 在这里执行删除数据的操作
                // 这里需要根据你的实际需求处理
                // 如果执行了删除数据的操作，可以在这里调用 navController.popBackStack()
                // 来返回主页
                LedgerDatabase.launch {
                    val ledgerDao = db.ledgerDao()
                    ledgerDao.deleteAllLedgers()
                }
                openDialog = false
            }
        )
    }

    if (openInfoDialog) {
        InfoDialog(
            onDismissRequest = {
                // 关闭弹窗
                openInfoDialog = false
            },
            onConfirmClick = {
                // 在这里执行删除数据的操作
                // 这里需要根据你的实际需求处理
                // 如果执行了删除数据的操作，可以在这里调用 navController.popBackStack()
                // 来返回主页
                openInfoDialog = false
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteDataDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "确定要删除所有数据吗？",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismissRequest
                    ) {
                        Text("取消")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        onClick = onConfirmClick
                    ) {
                        Text("确定")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InfoDialog(
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Made By ShouHeng and \uD83D\uDC93.",
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Spacer(modifier = Modifier.width(16.dp))
                    TextButton(
                        onClick = onConfirmClick
                    ) {
                        Text("确定")
                    }
                }
            }
        }
    }
}