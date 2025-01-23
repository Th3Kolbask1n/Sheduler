package com.alexp.sheduler.data

import com.alexp.sheduler.presentation.AttendanceRecord
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class AttendanceRecordsMapper @Inject constructor() {

    fun mapEntityToDbModel(attendanceRecord: AttendanceRecord) =
        AttendanceRecordDbModel(
            timeIn = attendanceRecord.timeIn,
            timeOut = attendanceRecord.timeOut,
            date = formatDateToBase(attendanceRecord.date),
            id = attendanceRecord.id
        )


    fun mapDbModelToEntity(attendanceRecordDbModel: AttendanceRecordDbModel) =
        AttendanceRecord(
            id = attendanceRecordDbModel.id,
            timeOut = attendanceRecordDbModel.timeOut,
            timeIn = attendanceRecordDbModel.timeIn,
            date = formatDateFromBase(attendanceRecordDbModel.date)
        )


    fun mapListDbModelToListEnity(list: List<AttendanceRecordDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

    fun formatDateFromBase(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)

        return outputFormat.format(date!!)
    }
    fun formatDateToBase(inputDate: String): String {
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        val date = inputFormat.parse(inputDate)

        return outputFormat.format(date!!)
    }
}