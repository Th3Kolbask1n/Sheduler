package com.alexp.sheduler.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.alexp.sheduler.presentation.AttendanceRecord
import com.alexp.sheduler.domain.SchedulerRepository
import javax.inject.Inject

class SchedulerRepositoryImpl @Inject constructor(
    private val attendanceRecordsMapper: AttendanceRecordsMapper,
    private val schedulerRepository: SchedulerDao

):  SchedulerRepository {
    override suspend fun addAttendanceRecord(attendanceRecord: AttendanceRecord) {
        schedulerRepository.addAttendanceRecord(attendanceRecordsMapper.mapEntityToDbModel(attendanceRecord))
    }

    override suspend fun deleteAttendanceRecord(attendanceRecord: AttendanceRecord) {
        schedulerRepository.deleteAttendanceRecord(attendanceRecord.id)
    }

    override fun getAttendanceRecordList(): LiveData<List<AttendanceRecord>>  =
      schedulerRepository.getAttendanceRecordList().map {
          attendanceRecordsMapper.mapListDbModelToListEnity(it)
      }

    override suspend fun editAttendanceRecord(attendanceRecord: AttendanceRecord) {
        schedulerRepository.addAttendanceRecord(attendanceRecordsMapper.mapEntityToDbModel(attendanceRecord))
    }

    override suspend fun getAttendanceRecord(attendanceRecordId: Int): AttendanceRecord {
        return attendanceRecordsMapper.mapDbModelToEntity(
        schedulerRepository.getAttendanceRecord(attendanceRecordId))

    }



}