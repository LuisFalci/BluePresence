package com.example.a16_room.ui.view.subject

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.a16_room.data.models.ScheduleModel
import com.example.a16_room.databinding.ActivityEditSubjectBinding
import com.example.a16_room.ui.viewmodels.ScheduleViewModel
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class EditSubjectActivity : AppCompatActivity() {
    private lateinit var subjectViewModel: SubjectViewModel
    private lateinit var scheduleViewModel: ScheduleViewModel

    private lateinit var selectedButton: Button

    private lateinit var binding: ActivityEditSubjectBinding
    private var subjectId: Long = -1L

    data class ClassTime(
        val dayOfWeek: String = "",
        var startTime: String = "",
        var endTime: String = "",
    )

    private lateinit var scheduleDataList: List<ScheduleModel>
    private val classTimesList = mutableListOf<CreateSubjectActivity.ClassTime>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Editar Turma"

        subjectViewModel = ViewModelProvider(this).get(SubjectViewModel::class.java)

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }
        if (subjectId > 0) {
            subjectViewModel.get(subjectId)
            subjectViewModel.subject.observe(this) { subject ->
                subjectId = subject.subjectId
                binding.editName.setText(subject.subjectName)
            }
        }

        binding.buttonEdit.setOnClickListener {
            val name = binding.editName.text.toString()
            subjectViewModel.update(subjectId, name)

            finish()
        }
    }
}