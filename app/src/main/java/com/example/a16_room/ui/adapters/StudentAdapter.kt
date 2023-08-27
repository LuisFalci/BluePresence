package com.example.a16_room.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a16_room.ui.listeners.OnStudentListener
import com.example.a16_room.ui.viewholders.StudentViewHolder
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.databinding.RowStudentBinding

class StudentAdapter : RecyclerView.Adapter<StudentViewHolder>() {

    private var studentList: List<StudentModel> = listOf()
    private lateinit var listener: OnStudentListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val item = RowStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(studentList[position])
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    fun updateStudents(list: List<StudentModel>) {
        studentList = list
        notifyDataSetChanged()
    }

    fun attachListener(studentListener: OnStudentListener) {
        listener = studentListener
    }

}