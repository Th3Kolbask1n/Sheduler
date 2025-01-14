package com.alexp.sheduler.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexp.sheduler.data.AttendanceRecordDbModel

@Dao
interface SchedulerRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAttendanceRecord(attendanceRecordDbModel: AttendanceRecordDbModel)

    @Query("DELETE FROM attendance_records WHERE id=:attendanceRecordId")

    suspend fun deleteAttendanceRecord(attendanceRecordId: Int)

    @Query("SELECT * FROM attendance_records")

    fun getAttendanceRecordList(): LiveData<List<AttendanceRecordDbModel>>

    @Query("SELECT * FROM attendance_records WHERE id +:attendanceRecordId LIMIT 1")
    suspend fun getAttendanceRecord(attendanceRecordId: Int): AttendanceRecordDbModel


}