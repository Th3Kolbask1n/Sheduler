package com.alexp.sheduler.domain

import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class EditRecordUseCase @Inject constructor(
    private val repository: SchedulerRepository
) {

    suspend fun editAttendanceRecord(attendanceRecord: AttendanceRecord) {
        return repository.editAttendanceRecord(attendanceRecord)
    }
}