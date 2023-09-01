package com.example.a16_room.ui.view.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.databinding.ActivityEditStudentBinding
import com.example.a16_room.ui.viewmodels.StudentViewModel

class EditStudentActivity : AppCompatActivity() {
    private lateinit var viewModel: StudentViewModel
    private var studentId: Long = -1L
    private lateinit var binding: ActivityEditStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]

        if (intent.hasExtra("student_id")) {
            studentId = intent.getLongExtra(("student_id"), -1L)
        }
        if (studentId > 0) {
            viewModel.get(studentId)
            viewModel.student.observe(this) { student ->
                studentId = student.id
                binding.editName.setText(student.name)
                binding.editRegistration.setText(student.registration)
            }
        }

        binding.buttonEdit.setOnClickListener {
            val name = binding.editName.text.toString()
            val registration = binding.editRegistration.text.toString()
            viewModel.update(studentId, name, registration)

            finish()
        }

    }
}