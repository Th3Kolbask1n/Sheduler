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
import kotlinx.coroutines.Job
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

    private val dateFormat = SimpleDateFormat("MMMM yyyy", Locale("ru", "RU"))

    private var loadJob: Job? = null

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
        val monthNames = arrayOf(
            "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
            "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        )
        val currentMonthIndex = calendar.get(Calendar.MONTH)
        val month = monthNames[currentMonthIndex]
        val year = calendar.get(Calendar.YEAR).toString()

        _currentMonth.value = month
        _currentYear.value = year
        loadRecords((currentMonthIndex + 1).toString().padStart(2, '0'), year)
    }

    private fun loadRecords(month: String, year: String)
    {

        loadJob?.cancel() // Отменяем предыдущий запрос, если он еще выполняется
        loadJob = viewModelScope.launch {
            getRecordsByMonthUseCase.getAttendanceRecordListByMonth(month, year)
                .collect { recordList ->
                    _recordList.value = recordList
                }
        }
    }

    private fun getMonthNumber(month: String): String {
        val monthMap = mapOf(
            "Январь" to "01",
            "Февраль" to "02",
            "Март" to "03",
            "Апрель" to "04",
            "Май" to "05",
            "Июнь" to "06",
            "Июль" to "07",
            "Август" to "08",
            "Сентябрь" to "09",
            "Октябрь" to "10",
            "Ноябрь" to "11",
            "Декабрь" to "12"
        )
        return monthMap[month] ?: "01"
    }
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}