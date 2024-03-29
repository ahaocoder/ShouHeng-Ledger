package com.example.ledger.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ledger.R
import com.example.ledger.data.database.LedgerDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(navController: NavController, db: LedgerDatabase) {
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

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        val ledgerDao = db.ledgerDao()
        val ledgerList by ledgerDao.getAllLedgers().collectAsState(initial = listOf())
        // 分离出true和false的项
        val trueLedgers = ledgerList.filter { it.isIncome }
        val falseLedgers = ledgerList.filter { !it.isIncome }

        // 求和
        val trueSum = trueLedgers.sumByDouble { it.amount }
        val falseSum = falseLedgers.sumByDouble { it.amount }

        // 输出结果
        Text(text = "消费的金额总和: $falseSum 元", Modifier.padding(top = 80.dp))
        Text(text = "收入的金额总和: $trueSum 元")
        Button(modifier = Modifier.padding(top = 80.dp), onClick = {
            navController.popBackStack()
        }) {
            Text(text = "返回主页")
        }
    }
}
