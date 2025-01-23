package com.alexp.sheduler.presentation

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexp.sheduler.domain.DeleteRecordUseCase
import com.alexp.sheduler.domain.EditRecordUseCase
import com.alexp.sheduler.domain.GetRecordUseCase
import com.alexp.sheduler.domain.GetRecordsByMonthUseCase
import com.alexp.sheduler.domain.GetRecordsUseCase
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class MainViewModel  @Inject constructor(
    private val getRecordsByMonthUseCase: GetRecordsByMonthUseCase,
    private val deleteRecordUseCase: DeleteRecordUseCase,
    private val editRecordUseCase: EditRecordUseCase
) :ViewModel(){

    private val _recordList = MutableStateFlow<List<AttendanceRecord>>(emptyList())
    val recordList : StateFlow<List<AttendanceRecord>> = _recordList

//    private val _calendar = MutableStateFlow(Calendar.getInstance())
    private val calendar = Calendar.getInstance()


    private val _currentMonth = MutableStateFlow("")
    val currentMonth: StateFlow<String> = _currentMonth

    private val _currentYear = MutableStateFlow("")
    val currentYear: StateFlow<String> = _currentYear

    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())



    init {
        updateMonthYear()
    }
    fun deleteAttendanceRecord (attendanceRecord: AttendanceRecord)
    {
        viewModelScope.launch {

            deleteRecordUseCase.deleteAttendanceRecord(attendanceRecord)
        }
    }
    fun changeMonth(delta: Int)
    {
        calendar.add(Calendar.MONTH, delta)
        updateMonthYear()

    }

    private fun updateMonthYear() {
        val monthYear = dateFormat.format(calendar.time)
        val (month, year) = monthYear.split(" ")
        _currentMonth.value = month
        _currentYear.value = year
        loadRecords(getMonthNumber(month),year)
    }

    private fun loadRecords(month: String, year: String)
    {

        viewModelScope.launch {
            getRecordsByMonthUseCase.getAttendanceRecordListByMonth(month,year)
                .collect{
                    recordList ->
                    _recordList.value = recordList

                }
        }
    }

    private fun getMonthNumber(month: String): String {
        val monthMap = mapOf(
            "January" to "01",
            "February" to "02",
            "March" to "03",
            "April" to "04",
            "May" to "05",
            "June" to "06",
            "July" to "07",
            "August" to "08",
            "September" to "09",
            "October" to "10",
            "November" to "11",
            "December" to "12"
        )
        return monthMap[month] ?: "01"
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}