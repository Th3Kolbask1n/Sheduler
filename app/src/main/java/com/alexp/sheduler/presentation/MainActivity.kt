package com.alexp.sheduler.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: AttendanceRecordViewModel

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


        binding.addAttendanceRecordButton.setOnClickListener {
            val intent = AttendanceRecordActivity.newIntentAdd(this)
            startActivity(intent)

        }

        viewModel = ViewModelProvider(this,viewModelFactory)[AttendanceRecordViewModel::class.java]
    }


}