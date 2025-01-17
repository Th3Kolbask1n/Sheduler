package com.alexp.sheduler.domain

import androidx.lifecycle.LiveData
import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class GetRecordsUseCase @Inject constructor(
    private val repository: SchedulerRepository
) {

    fun getAttendanceRecordList(): LiveData<List<AttendanceRecord>> {
        return repository.getAttendanceRecordList()
    }
}