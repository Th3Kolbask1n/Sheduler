package com.alexp.sheduler.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alexp.sheduler.R

class AttendanceRecordActivity : AppCompatActivity(), AttendanceRecordFragment.OnEditingFinishedListener {
    private var screenMode = MODE_UNKNOWN
    private var attendanceRecordId = AttendanceRecord.UNDEFINDED_ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_atten_dance_record)

    parseIntent()
        launchRightMode()
    }

    fun launchRightMode()
    {

        val fragment =
            when(screenMode){
                MODE_ADD -> AttendanceRecordFragment.newIntentAdd()
                MODE_EDIT -> AttendanceRecordFragment.newIntentEdit(attendanceRecordId)
                else->
                    throw RuntimeException("Unknown mode")
            }

            AttendanceRecordFragment.newIntentAdd()

        supportFragmentManager.beginTransaction()
            .replace(R.id.attendance_record_container,fragment)
            .commit()
    }

    private fun parseIntent()
    {
        if(!intent.hasExtra(EXTRA_SCREEN_MODE))
        {
            throw RuntimeException("Param screen mode absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if(mode!= MODE_EDIT && mode!= MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode
        if(screenMode == MODE_EDIT)
        {
            if(!intent.hasExtra(EXTRA_RECORD_ID))
                throw RuntimeException("Param shop item id absent")

            attendanceRecordId = intent.getIntExtra(EXTRA_RECORD_ID, AttendanceRecord.UNDEFINDED_ID)
        }



    }
    override fun onEditingFinished() {
        Toast.makeText(this,"Успешно",Toast.LENGTH_SHORT).show()
        finish()
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