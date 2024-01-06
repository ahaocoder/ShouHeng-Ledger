// src/main/java/com/example/ledger/data/dao/LedgerDao.kt

package com.example.ledger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ledger.data.model.Ledger

@Dao
interface LedgerDao {

    @Query("SELECT * FROM ledgers")
    fun getAllLedgers(): List<Ledger>

    @Insert
    fun insertLedger(ledger: Ledger)

    // 添加其他需要的查询或操作
}
