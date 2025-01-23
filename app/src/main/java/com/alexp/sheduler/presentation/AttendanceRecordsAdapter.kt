package com.alexp.sheduler.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.ListItemBinding

class AttendanceRecordsAdapter :
    ListAdapter<AttendanceRecord, AttendanceRecordsAdapterViewHolder>(AttendanceRecordDiffCallback()) {


    var onAttendanceRecordClickListener: ((AttendanceRecord) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttendanceRecordsAdapterViewHolder {


        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item,
            parent,
            false
        )


        return AttendanceRecordsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttendanceRecordsAdapterViewHolder, position: Int) {

        val attendanceRecord = getItem(position)
        val binding = holder.binding
        binding.root.setOnClickListener {
            onAttendanceRecordClickListener?.invoke(attendanceRecord)
        }
        if (binding is ListItemBinding) {
            binding.attendanceRecord = attendanceRecord
        }

    }


    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 50
    }
}