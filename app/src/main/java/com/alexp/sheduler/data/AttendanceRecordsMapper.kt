package com.alexp.sheduler.data

import com.alexp.sheduler.presentation.AttendanceRecord
import javax.inject.Inject

class AttendanceRecordsMapper @Inject constructor() {

    fun mapEntityToDbModel(attendanceRecord: AttendanceRecord) =
        AttendanceRecordDbModel(
            timeIn = attendanceRecord.timeIn,
            timeOut = attendanceRecord.timeOut,
            date = attendanceRecord.date,
            id = attendanceRecord.id
        )


    fun mapDbModelToEntity(attendanceRecordDbModel: AttendanceRecordDbModel) =
        AttendanceRecord(
            id = attendanceRecordDbModel.id,
            timeOut = attendanceRecordDbModel.timeOut,
            timeIn = attendanceRecordDbModel.timeIn,
            date = attendanceRecordDbModel.date
        )


    fun mapListDbModelToListEnity(list: List<AttendanceRecordDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}