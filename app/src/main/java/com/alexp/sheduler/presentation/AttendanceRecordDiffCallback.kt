package com.alexp.sheduler.presentation

import androidx.recyclerview.widget.DiffUtil

class AttendanceRecordDiffCallback : DiffUtil.ItemCallback<AttendanceRecord>() {

    override fun areItemsTheSame(oldItem: AttendanceRecord, newItem: AttendanceRecord): Boolean {
        return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AttendanceRecord, newItem: AttendanceRecord): Boolean {

        return oldItem == newItem
    }

}