package com.alexp.sheduler.domain

import androidx.lifecycle.LiveData
import com.alexp.sheduler.presentation.AttendanceRecord

interface SchedulerRepository {

    suspend fun addAttendanceRecord(attendanceRecord: AttendanceRecord)
    suspend fun deleteAttendanceRecord(attendanceRecord: AttendanceRecord)
    fun getAttendanceRecordList() : LiveData<List<AttendanceRecord>>
    suspend fun editAttendanceRecord(attendanceRecord: AttendanceRecord)
    suspend fun getAttendanceRecord(attendanceRecordId:Int) : AttendanceRecord

}