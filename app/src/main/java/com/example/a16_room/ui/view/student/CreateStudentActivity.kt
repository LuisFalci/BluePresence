package com.example.a16_room.ui.view.student

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.databinding.ActivityCreateStudentBinding
import com.example.a16_room.ui.viewmodels.StudentViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateStudentActivity : AppCompatActivity() {
    private lateinit var viewModel: StudentViewModel
    private lateinit var subjectViewModel: SubjectViewModel

    lateinit var binding: ActivityCreateStudentBinding
    private var subjectId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        subjectViewModel =
            ViewModelProvider(this)[SubjectViewModel::class.java] // Assuming you have a SubjectViewModel

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        binding.buttonInsert.setOnClickListener {
            val name = binding.editName.text.toString()
            val registration = binding.editRegistration.text.toString()

            val insertedStudentId = viewModel.insert(name, registration)

            if (insertedStudentId > 0 && subjectId != -1L) {
                subjectViewModel.insertStudentSubject(insertedStudentId, subjectId)
            }

            finish()
        }
    }
}
