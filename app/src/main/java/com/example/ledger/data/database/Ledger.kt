package com.example.ledger.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ledger")
data class Ledger(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = 0L,

    @ColumnInfo(name = "category")
    val category: String,    // 存储大类，比如餐饮

    @ColumnInfo(name = "tag")
    val tag: String,         // 存储细节，比如早餐

    @ColumnInfo(name = "description")
    val description: String, // 用于记录更详细的信息

    @ColumnInfo(name = "amount")
    val amount: Double,      // 金额

    @ColumnInfo(name = "isIncome")
    val isIncome: Boolean,   // 支出与收入的状态标识

    @ColumnInfo(name = "dayTimestamp")
    val dayTimestamp: Long,   // 天时间戳，当日0点的时间戳

    @ColumnInfo(name = "timeTimestamp")
    val timeTimestamp: Long   // 时间时间戳
)
