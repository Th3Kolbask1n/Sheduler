package com.alexp.sheduler.presentation

import android.icu.util.LocaleData
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.sheduler.domain.AddRecordUseCase
import com.alexp.sheduler.domain.DeleteRecordUseCase
import com.alexp.sheduler.domain.EditRecordUseCase
import com.alexp.sheduler.domain.GetRecordUseCase
import com.alexp.sheduler.domain.GetRecordsByMonthUseCase
import com.alexp.sheduler.domain.GetRecordsUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject


class AttendanceRecordViewModel @Inject constructor(
    val addRecordUseCase: AddRecordUseCase,
    val editRecordUseCase: EditRecordUseCase,
    val getRecordUseCase: GetRecordUseCase,
    val getRecordsUseCase: GetRecordsUseCase,
    val getRecordsByMonthUseCase: GetRecordsByMonthUseCase
) : ViewModel() {

//    val attendanceRecordList = getRecordsUseCase.getAttendanceRecordList()

    private val _attendanceRecord = MutableLiveData<AttendanceRecord>()

    val attendanceRecord:LiveData<AttendanceRecord>
        get() = _attendanceRecord

    private val _attendanceRecordList = MutableLiveData<List<AttendanceRecord>>()

    val attendanceRecordList:LiveData<List<AttendanceRecord>>
        get() = _attendanceRecordList

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private var _currentMonth = MutableLiveData<LocalDate>(LocalDate.now())

    val currentMonth:LiveData<LocalDate>
        get() = _currentMonth

    private val _currentDate = MutableLiveData<String>()
    val currentDate: LiveData<String> get() = _currentDate

    private val _currentTime = MutableLiveData<String>()
    val currentTime: LiveData<String> get() = _currentTime

    init {
        setCurrentDateTime()
    }


    fun setCurrentDateTime() {
        val calendar = Calendar.getInstance()
        val date = String.format(
            "%02d-%02d-%02d",
            calendar.get(Calendar.DAY_OF_MONTH),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.YEAR)
        )
        val time = String.format(
            "%02d:%02d",
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE)
        )

        _currentDate.value = date
        _currentTime.value = time
    }

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

            val record = AttendanceRecord(date = date, timeIn = timeIn, timeOut = timeOut)
            addRecordUseCase.addAttendanceRecord(record)
            finishWork()
        }
    }


    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}