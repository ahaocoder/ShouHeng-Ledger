// src/main/java/com/example/ledger/data/entities/Ledger.kt

package com.example.ledger.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "ledgers")
data class Ledger(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Date,
    val category: String,
    val tags: String,
    val amount: Double,
    val isIncome: Boolean,
    val additionalNotes: String?
)
