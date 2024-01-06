// src/main/java/com/example/ledger/data/AppDatabase.kt

package com.example.ledger.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ledger.data.converters.DateConverter
import com.example.ledger.data.dao.LedgerDao
import com.example.ledger.data.model.Ledger

@Database(entities = [Ledger::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class) // 注册类型转换器
abstract class AppDatabase : RoomDatabase() {

    abstract fun ledgerDao(): LedgerDao

    // 添加其他需要的数据库操作
}
