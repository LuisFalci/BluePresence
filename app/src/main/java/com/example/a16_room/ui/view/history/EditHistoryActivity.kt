package com.example.a16_room.ui.view.history

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef
import com.example.a16_room.databinding.ActivityEditHistoryBinding
import com.example.a16_room.ui.adapters.EditHistoryAdapter
import com.example.a16_room.ui.listeners.OnAttendanceListener
import com.example.a16_room.ui.viewmodels.AttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentAttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentViewModel

class EditHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditHistoryBinding

    private lateinit var studentViewModel: StudentViewModel
    private lateinit var attendanceAdapter: EditHistoryAdapter
    private lateinit var attendanceViewModel: AttendanceViewModel
    private lateinit var studentAttendanceViewModel: StudentAttendanceViewModel

    private val studentList: MutableList<StudentModel> = mutableListOf()
    private val studentAttendanceMap = mutableMapOf<Long, Boolean>()
    private val studentAttendanceList: MutableList<StudentAttendanceCrossRef> = mutableListOf()


    private var attendanceId: Long = -1L
    private var subjectId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Editar Chamada"

        if (intent.hasExtra("attendanceId")) {
            attendanceId = intent.getLongExtra("attendanceId", -1L)
        }
        if (intent.hasExtra("subjectId")) {
            subjectId = intent.getLongExtra("subjectId", -1L)
        }
        studentViewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]
        studentAttendanceViewModel = ViewModelProvider(this)[StudentAttendanceViewModel::class.java]

        Log.d("attendanceIddsa", "${subjectId}")
        Log.d("attendanceIddsa", "${attendanceId}")

        studentViewModel.getAllStudentsInSubject(subjectId)

        studentViewModel.students.observe(this) { students ->
            studentList.clear()
            studentList.addAll(students)
            binding.totalStudents.text = "Total de alunos: " + studentList.size.toString()
            attendanceAdapter.notifyDataSetChanged()
        }

        val recyclerViewStudent = binding.studentsAttendance
        recyclerViewStudent.layoutManager = LinearLayoutManager(this)

        attendanceAdapter = EditHistoryAdapter(this, studentList)
        recyclerViewStudent.adapter = attendanceAdapter

        binding.seveAttendance.setOnClickListener {
            var dateTime: Long = System.currentTimeMillis()
            val totalStudents = studentAttendanceMap.size
            val totalPresents = calculateTotalStudentsPresent()
            val attendanceId =
                attendanceViewModel.insert(subjectId, dateTime, totalStudents, totalPresents)

            for ((studentId, isPresent) in studentAttendanceMap) {
                studentAttendanceViewModel.insert(attendanceId, subjectId, studentId, isPresent)
            }
            finish()
        }

        val listener = object : OnAttendanceListener {
            override fun onStudentClick(studentId: Long, isPresent: Boolean) {
//                studentAttendanceMap[studentId] = isPresent
                Log.d("presente", "${isPresent}")
            }
        }
        attendanceAdapter.attachListener(listener)
    }

    private fun calculateTotalStudentsPresent(): Int {
        return studentAttendanceMap.count { it.value }
    }

}