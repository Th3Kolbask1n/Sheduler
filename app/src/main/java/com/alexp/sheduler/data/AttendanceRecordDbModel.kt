package com.alexp.sheduler.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attendance_records")
class AttendanceRecordDbModel
    (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timeIn: String,
    val timeOut: String,
    val date : String
            ) {
}