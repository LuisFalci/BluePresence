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
    private val studentRadioButtonStateMap = mutableMapOf<Long, Boolean>()

    inner class AttendanceViewHolder(binding: RowAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val studentName = binding.studentNameTextView
        val studentPresent = binding.radioPresent
        val studentAbsent = binding.radioAbsent
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

        holder.studentPresent.setOnCheckedChangeListener(null)
        holder.studentAbsent.setOnCheckedChangeListener(null)

        val isPresent = studentRadioButtonStateMap.getOrDefault(student.studentId, false)
        if (bluetoothDevicesFound.contains(student.macAddress)) {
            studentRadioButtonStateMap[student.studentId] = true
        }
        holder.studentPresent.isChecked = isPresent
        holder.studentAbsent.isChecked = !isPresent

        holder.studentPresent.setOnCheckedChangeListener { buttonView, isChecked ->
            studentRadioButtonStateMap[student.studentId] = isChecked
            listener.onStudentClick(student.studentId, isChecked)
        }

        holder.studentAbsent.setOnCheckedChangeListener { buttonView, isChecked ->
            studentRadioButtonStateMap[student.studentId] = !isChecked
            listener.onStudentClick(student.studentId, !isChecked)
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