package com.example.a16_room.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.databinding.RowAttendanceBinding
import com.example.a16_room.ui.listeners.OnAttendanceListener
import java.text.SimpleDateFormat
import java.util.Locale

class AttendanceAdapter(
    private val context: Context,
    private val studentList: MutableList<StudentModel>,
    private var bluetoothDevicesFound: List<String>
) : RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {
    private lateinit var listener: OnAttendanceListener

    inner class AttendanceViewHolder(binding: RowAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val studentName = binding.studentNameTextView
        val studentPresent = binding.checkBoxPresent
        val studentAbsent = binding.checkBoxAbsent
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttendanceAdapter.AttendanceViewHolder {
        val itemList = RowAttendanceBinding.inflate(LayoutInflater.from(context), parent, false)
        return AttendanceViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: AttendanceAdapter.AttendanceViewHolder, position: Int) {
        val student = studentList[position]
        holder.studentName.text = student.name
        val studentMacAddress = student.macAddress
        holder.studentPresent.isChecked = bluetoothDevicesFound.contains(studentMacAddress)
        holder.studentPresent.setOnClickListener {
            listener.onStudentClick(student.id, true)
        }
        holder.studentAbsent.setOnClickListener {
            listener.onStudentClick(student.id, false)
        }

        Log.d("erro", "${student.macAddress} e ${student.name}")
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun updateBluetoothDevicesFound(updatedBluetoothDevicesFound: List<String>) {
        bluetoothDevicesFound = updatedBluetoothDevicesFound.toMutableList()
        notifyDataSetChanged()
    }

    private fun getCurrentTimestamp(): String {
        val currentTimeMillis = System.currentTimeMillis()
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            .format(currentTimeMillis)
    }

    fun attachListener(attendanceListener: OnAttendanceListener) {
        listener = attendanceListener
    }
}