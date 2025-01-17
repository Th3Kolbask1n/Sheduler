package com.alexp.sheduler.domain

import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class AddRecordUseCase @Inject constructor(
    private val repository: SchedulerRepository
) {

    suspend fun addAttendanceRecord(attendanceRecord: AttendanceRecord) {
        return repository.addAttendanceRecord(attendanceRecord)
    }
}

