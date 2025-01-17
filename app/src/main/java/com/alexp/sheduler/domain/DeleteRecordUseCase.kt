package com.alexp.sheduler.domain

import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val repository: SchedulerRepository
) {

    suspend fun deleteAttendanceRecord(attendanceRecord: AttendanceRecord) {
        return repository.deleteAttendanceRecord(attendanceRecord)
    }
}