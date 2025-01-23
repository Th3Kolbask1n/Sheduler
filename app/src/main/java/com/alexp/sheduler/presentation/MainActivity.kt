package com.alexp.sheduler.presentation

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    private lateinit var recordsAdapter : AttendanceRecordsAdapter
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)

    }
    private val component by lazy{
        (application as AttendanceApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val dateFormat = SimpleDateFormat("LLLL yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)

        setContentView(binding.root)
        setupRecyclerView()
        setupClickListener()

        binding.addAttendanceRecordButton.setOnClickListener {
            val intent = AttendanceRecordActivity.newIntentAdd(this)
            startActivity(intent)

        }

        binding.buttonNextMonth.setOnClickListener {

            lifecycleScope.launch {
                viewModel.changeMonth(1)
            }
        }

        binding.buttonPreviousMonth.setOnClickListener {

            lifecycleScope.launch {
                viewModel.changeMonth(-1)
            }
        }
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recordList.collect {

                    recordsAdapter.submitList(it)
                }
            }

        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.currentMonth.collect {month ->
                    val year = viewModel.currentYear.value
                    binding.textCurrentMonth.text = "$month $year"

                }
            }

        }
    }

    private fun setupRecyclerView()
    {
        with(binding.recyclerView)
        {
            recordsAdapter = AttendanceRecordsAdapter()
            adapter = recordsAdapter
        }
        setupSwipeListeners(binding.recyclerView)

    }

    private fun setupSwipeListeners(rvRecordList:RecyclerView?)
    {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val record = recordsAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteAttendanceRecord(record)
            }


        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvRecordList)
    }
    private fun setupClickListener() {

        recordsAdapter.onAttendanceRecordClickListener = {

            val intent = AttendanceRecordActivity.newIntentEdit(this,it.id)
                startActivity(intent)

        }
    }

}