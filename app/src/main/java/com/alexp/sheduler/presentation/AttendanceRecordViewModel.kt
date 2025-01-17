package com.alexp.sheduler.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.sheduler.domain.AddRecordUseCase
import com.alexp.sheduler.domain.DeleteRecordUseCase
import com.alexp.sheduler.domain.EditRecordUseCase
import com.alexp.sheduler.domain.GetRecordUseCase
import com.alexp.sheduler.domain.GetRecordsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


class AttendanceRecordViewModel @Inject constructor(
    val addRecordUseCase: AddRecordUseCase,
    val editRecordUseCase: EditRecordUseCase,
    val getRecordUseCase: GetRecordUseCase,
    val getRecordsUseCase: GetRecordsUseCase
) : ViewModel() {

    val attendanceRecordList = getRecordsUseCase.getAttendanceRecordList()

    private val _attendanceRecord = MutableLiveData<AttendanceRecord>()
    val attendanceRecord
        get() = attendanceRecordList

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getAttendanceRecord(recordId: Int) {
        viewModelScope.launch {
            val record = getRecordUseCase.getAttendanceRecord(recordId)
            _attendanceRecord.value = record
        }
    }

    fun editAttendanceRecord(date: String, timeIn: String, timeOut: String) {
        viewModelScope.launch {
            _attendanceRecord.value?.let {
                val record = it.copy(date = date, timeIn = timeIn, timeOut = timeOut)
                editRecordUseCase.editAttendanceRecord(record)
                finishWork()
            }
        }
    }

    fun addAttendanceRecord(date: String, timeIn: String, timeOut: String) {

        viewModelScope.launch {
            Log.d("Kitty2", date)

            val record = AttendanceRecord(date = date, timeIn = timeIn, timeOut = timeOut)
            addRecordUseCase.addAttendanceRecord(record)
            finishWork()
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}