package com.alexp.sheduler.domain

import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class GetRecordUseCase @Inject constructor(
    private val repository: SchedulerRepository
) {

    suspend fun getAttendanceRecord(recordId : Int): AttendanceRecord {
        return repository.getAttendanceRecord(recordId)
    }
}