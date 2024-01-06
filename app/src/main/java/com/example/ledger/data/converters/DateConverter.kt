// src/main/java/com/example/ledger/data/converters/DateConverter.kt

package com.example.ledger.data.converters

import androidx.room.TypeConverter
import java.util.Date

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}
