package com.example.a16_room.ui.view.subject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.databinding.ActivityEditSubjectBinding
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class EditSubjectActivity : AppCompatActivity() {
    private lateinit var viewModel: SubjectViewModel
    private var subjectId: Long = -1L
    private lateinit var binding: ActivityEditSubjectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }
        if (subjectId > 0) {
            viewModel.get(subjectId)
            viewModel.subject.observe(this) { subject ->
                subjectId = subject.subjectId
                binding.editName.setText(subject.subjectName)
            }
        }

        binding.buttonEdit.setOnClickListener {
            val name = binding.editName.text.toString()
            viewModel.update(subjectId, name)

            finish()
        }
    }
}