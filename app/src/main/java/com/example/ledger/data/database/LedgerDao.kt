package com.example.ledger.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface LedgerDao {
    @Insert
    fun insertLedger(ledger: Ledger)

    @Query("SELECT * FROM ledger")
    fun getAllLedgers(): List<Ledger>

    @Update
    fun updateLedger(ledger: Ledger)

    // 删除单条记录
    @Query("DELETE FROM ledger WHERE id = :entryId")
    fun deleteLedger(entryId: Long)

    // 用日时间戳查询当天记录
    @Query("SELECT * FROM ledger WHERE dayTimestamp = :dayTimestamp")
    fun getLedgersByDay(dayTimestamp: Long): List<Ledger>

    // 查询月记录
    @Query("SELECT * FROM ledger WHERE dayTimestamp >= :startOfMonth AND dayTimestamp < :startOfNextMonth")
    fun getLedgersByMonth(startOfMonth: Long, startOfNextMonth: Long): List<Ledger>
}
