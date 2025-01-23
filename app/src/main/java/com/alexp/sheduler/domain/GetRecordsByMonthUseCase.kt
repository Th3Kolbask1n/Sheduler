package com.alexp.sheduler.domain

import androidx.lifecycle.LiveData
import com.alexp.sheduler.presentation.AttendanceRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecordsByMonthUseCase @Inject constructor(
    val repository: SchedulerRepository
){

    suspend fun getAttendanceRecordListByMonth(month: String, year: String): Flow<List<AttendanceRecord>>
    {
        return repository.getAttendanceRecordListByMonth(month, year)
    }
}