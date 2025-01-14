package com.alexp.sheduler.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexp.sheduler.R

class AttendanceRecordActivity : AppCompatActivity() {
    private var screenMode = MODE_UNKNOWN
    private var attendanceRecordId = AttendanceRecord.UNDEFINDED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atten_dance_record)


        launchRightMode()
    }

    fun launchRightMode()
    {
        val fragment = AttendanceRecordFragment.newIntentAdd(this)

        supportFragmentManager.beginTransaction()
            .replace(R.id.attendance_record_container,fragment)
            .commit()
    }
    companion object {


        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_RECORD_ID = "extra_attendance_record_id"

        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newIntentAdd(context: Context) : Intent
        {
            val intent = Intent(context, AttendanceRecordActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE,MODE_ADD)
            return intent
        }

        fun newIntentEdit(context: Context, recordId: Int) : Intent
        {
            val intent = Intent(context, AttendanceRecordActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE,MODE_EDIT)
            intent.putExtra(EXTRA_RECORD_ID, recordId)
            return intent
        }
    }


}