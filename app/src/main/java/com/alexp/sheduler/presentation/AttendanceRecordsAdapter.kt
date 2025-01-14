package com.alexp.sheduler.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.ListItemBinding

class AttendanceRecordsAdapter :
    RecyclerView.Adapter<AttendanceRecordsAdapter.AttendanceRecordsAdapterViewHolder>()
{

    val attendanceRecordList = emptyList<AttendanceRecord>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttendanceRecordsAdapterViewHolder {


        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            R.layout.list_item, // Используется один макет
            parent,
            false
        )


        return AttendanceRecordsAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AttendanceRecordsAdapterViewHolder, position: Int) {

            val attendanceRecord = attendanceRecordList[position]
            val binding = holder.binding

        if (binding is ListItemBinding) {
            binding.attendanceRecord = attendanceRecord
        }


    }

    override fun getItemCount(): Int {
        return attendanceRecordList.size
    }

    class AttendanceRecordsAdapterViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1
        const val MAX_POOL_SIZE = 5
    }
}