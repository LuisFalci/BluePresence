package com.example.a16_room.ui.view.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.databinding.ActivityCreateStudentBinding
import com.example.a16_room.ui.viewmodels.StudentViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class CreateStudentActivity : AppCompatActivity() {
    private lateinit var viewModel: StudentViewModel
    private lateinit var subjectViewModel: SubjectViewModel

    lateinit var binding: ActivityCreateStudentBinding
    private var subjectId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]
        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java] // Assuming you have a SubjectViewModel

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getIntExtra("subject_id", -1)
        }

        binding.buttonInsert.setOnClickListener {
            val name = binding.editName.text.toString()
            val registration = binding.editRegistration.text.toString()

            val insertedStudentId = viewModel.insert(name, registration)

            Toast.makeText(this, insertedStudentId.toString(), Toast.LENGTH_SHORT).show()

            if (insertedStudentId > 0 && subjectId != -1) {
                subjectViewModel.insertStudentSubject(insertedStudentId, subjectId)
            }

            finish()
        }
    }
}
