package com.alexp.sheduler.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alexp.sheduler.R
import com.alexp.sheduler.databinding.FragmentAttendanceRecordBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.jvm.Throws

class AttendanceRecordFragment : Fragment() {

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as AttendanceApplication).component
    }

    private var _binding: FragmentAttendanceRecordBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentRecord is null")

    private var isTimePickerShown = false

    private lateinit var viewModel: AttendanceRecordViewModel


    private var attendanceRecordId = AttendanceRecord.UNDEFINDED_ID
    private var screenMode = MODE_UNKNOW
    override fun onAttach(context: Context) {

        component.inject(this)
        super.onAttach(context)

        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }
    private fun launchRightMode()
    {
        when(screenMode)
        {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }
    private fun launchAddMode()
    {

        viewModel.currentDate.observe(viewLifecycleOwner) { date ->
            binding.editTextDate.setText(date)
        }
        viewModel.currentTime.observe(viewLifecycleOwner) { time ->
            binding.editTextCheckInTime.setText(time)
            binding.editTextCheckOutTime.setText(time)
        }

        binding.editTextDate.setOnClickListener {
            getDateDialog { selectedDate ->
                binding.editTextDate.setText(selectedDate)
            }
        }

        binding.editTextCheckInTime.setOnClickListener {
            getTimeDialog { selectedTime ->
                binding.editTextCheckInTime.setText(selectedTime)
            }
        }

        binding.editTextCheckOutTime.setOnClickListener {
            getTimeDialog { selectedTime ->
                binding.editTextCheckOutTime.setText(selectedTime)
            }
        }
        binding.btnSave.setOnClickListener {

            viewModel.addAttendanceRecord(binding.editTextDate.text.toString(),
                binding.editTextCheckInTime.text.toString(),
                binding.editTextCheckOutTime.text.toString())
        }
    }

    private fun launchEditMode()
    {

        viewModel.attendanceRecord.observe(viewLifecycleOwner) {
            binding.editTextDate.setText(it.date)
            binding.editTextCheckInTime.setText(it.timeIn)
            binding.editTextCheckOutTime.setText(it.timeOut)
        }

        viewModel.getAttendanceRecord(attendanceRecordId)




        binding.btnSave.setOnClickListener {
            viewModel.editAttendanceRecord(binding.editTextDate.text.toString(),
                binding.editTextCheckInTime.text.toString(),
                binding.editTextCheckOutTime.text.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = ViewModelProvider(this, viewModelFactory)[AttendanceRecordViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner)
        {
            onEditingFinishedListener.onEditingFinished()
        }
    }
    private fun setListeners() {
        binding.editTextDate.setOnClickListener {
            if (!isTimePickerShown)
                getDateDialog() {
                    binding.editTextDate.setText(it)
                }
        }

        binding.editTextCheckInTime.setOnClickListener {
            if (!isTimePickerShown)

                getTimeDialog {
                    binding.editTextCheckInTime.setText(it)
                }
        }
        binding.editTextCheckOutTime.setOnClickListener {
            if (!isTimePickerShown)

                getTimeDialog { binding.editTextCheckOutTime.setText(it) }
        }
        binding.btnSave.setOnClickListener {
            viewModel.addAttendanceRecord(
                binding.editTextDate.text.toString(),
                binding.editTextCheckInTime.text.toString(),
                binding.editTextCheckOutTime.text.toString()
            )
        }
    }

    private fun  parseParams()
    {
        val args = requireArguments()
        if(!args.containsKey(EXTRA_SCREEN_MODE))
        {
            throw RuntimeException("Param screen mode absent")


        }
        val mode = args.getString(EXTRA_SCREEN_MODE)

        if(mode!= MODE_EDIT && mode!= MODE_ADD)
            throw RuntimeException("Unknown screen mode $mode")

        screenMode = mode

        if(screenMode == MODE_EDIT)
        {
            if(!args.containsKey(EXTRA_RECORD_ID))
                throw RuntimeException("Param shop item id absent")


            attendanceRecordId = args.getInt(EXTRA_RECORD_ID, AttendanceRecord.UNDEFINDED_ID)

        }

    }
    private fun getDateDialog(onDateSet: (String) -> Unit) {
        if (isTimePickerShown) return

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = String.format("%02d-%02d-%02d", selectedDay, selectedMonth + 1, selectedYear)
                onDateSet(date)
            },
            year,
            month,
            day
        )

        datePickerDialog.setOnShowListener {
            isTimePickerShown = true
        }

        datePickerDialog.setOnDismissListener {
            isTimePickerShown = false
        }
        datePickerDialog.show()

    }

    private fun getTimeDialog(onTimeSet: (String) -> Unit) {
        if (isTimePickerShown) return

        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", selectedHour, selectedMinute)
                onTimeSet(time)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.setOnShowListener {
            isTimePickerShown = true
        }

        timePickerDialog.setOnDismissListener {
            isTimePickerShown = false
        }
        timePickerDialog.show()


    }
    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAttendanceRecordBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {


        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_RECORD_ID = "extra_attendance_record_id"

        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOW = ""


        fun newIntentAdd(): AttendanceRecordFragment {
            return AttendanceRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newIntentEdit(recordId: Int): AttendanceRecordFragment {

            return AttendanceRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_RECORD_ID, recordId)
                }
            }
        }
    }
}