package com.alexp.sheduler.domain

import androidx.lifecycle.LiveData
import com.alexp.sheduler.presentation.AttendanceRecord
import kotlinx.coroutines.flow.Flow

interface SchedulerRepository {

    suspend fun addAttendanceRecord(attendanceRecord: AttendanceRecord)
    suspend fun deleteAttendanceRecord(attendanceRecord: AttendanceRecord)
    fun getAttendanceRecordList() : Flow<List<AttendanceRecord>>
    suspend fun editAttendanceRecord(attendanceRecord: AttendanceRecord)
    suspend fun getAttendanceRecord(attendanceRecordId:Int) : AttendanceRecord

    fun getAttendanceRecordListByMonth(month: String, year: String): Flow<List<AttendanceRecord>>
}