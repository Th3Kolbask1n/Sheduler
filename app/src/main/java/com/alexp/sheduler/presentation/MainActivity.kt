package com.alexp.sheduler.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: AttendanceRecordViewModel
    private lateinit var recordsAdapter : AttendanceRecordsAdapter
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)

    }
    private val component by lazy{
        (application as AttendanceApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory




//    private fun isOnsePaneMode() : Boolean
//    {
//        return binding.c
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)

        setContentView(binding.root)
        setupRecyclerView()

        binding.addAttendanceRecordButton.setOnClickListener {
            val intent = AttendanceRecordActivity.newIntentAdd(this)
            startActivity(intent)

        }

        viewModel = ViewModelProvider(this,viewModelFactory)[AttendanceRecordViewModel::class.java]

        viewModel.attendanceRecordList.observe(this){

            recordsAdapter.submitList(it.toList())
        }

    }

    private fun setupRecyclerView()
    {
        with(binding.recyclerView)
        {
            recordsAdapter = AttendanceRecordsAdapter()
            adapter = recordsAdapter


        }

    }

}