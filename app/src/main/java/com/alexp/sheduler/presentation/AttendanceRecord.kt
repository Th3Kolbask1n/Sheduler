package com.alexp.sheduler.presentation

data class AttendanceRecord
    (
    val name: String,
    val timeIn: String,
    val timeOut: String,
    val date : String,
    val id: Int = UNDEFINDED_ID,
    ) {

    companion object {

        const val UNDEFINDED_ID = 0
    }

}