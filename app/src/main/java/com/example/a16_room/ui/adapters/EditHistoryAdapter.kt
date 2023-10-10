package com.example.a16_room.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.databinding.RowAttendanceBinding
import com.example.a16_room.ui.listeners.OnAttendanceListener

class EditHistoryAdapter(
    private val context: Context,
    private val studentList: List<StudentModel>,
    private val studentAttendanceMap: MutableMap<Long, Boolean>
) : RecyclerView.Adapter<EditHistoryAdapter.EditHistoryViewHolder>() {
    private lateinit var listener: OnAttendanceListener

    inner class EditHistoryViewHolder(binding: RowAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val studentName = binding.studentNameTextView
        val studentPresent = binding.radioPresent
        val studentAbsent = binding.radioAbsent
    }

    private val studentRadioButtonStateMap = mutableMapOf<Long, Boolean>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditHistoryAdapter.EditHistoryViewHolder {
        val itemList = RowAttendanceBinding.inflate(LayoutInflater.from(context), parent, false)
        return EditHistoryViewHolder(itemList)
    }

    override fun onBindViewHolder(holder: EditHistoryAdapter.EditHistoryViewHolder, position: Int) {
        val student = studentList[position]
        val studentId = student.studentId
        val studentAttendance = studentAttendanceMap[studentId]
        holder.studentName.text = student.name

        holder.studentPresent.setOnCheckedChangeListener(null)
        holder.studentAbsent.setOnCheckedChangeListener(null)

        holder.studentPresent.isChecked = studentAttendance == true
        holder.studentAbsent.isChecked = studentAttendance == false

        holder.studentPresent.setOnCheckedChangeListener { buttonView, isChecked ->
            studentRadioButtonStateMap[student.studentId] = isChecked
            listener.onStudentClick(studentId, isChecked)
        }

        holder.studentAbsent.setOnCheckedChangeListener { buttonView, isChecked ->
            studentRadioButtonStateMap[student.studentId] = !isChecked
            listener.onStudentClick(studentId, !isChecked)
        }
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun attachListener(attendanceListener: OnAttendanceListener) {
        listener = attendanceListener
    }
}