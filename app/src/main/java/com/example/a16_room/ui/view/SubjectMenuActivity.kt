package com.example.a16_room.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a16_room.databinding.ActivitySubjectMenuBinding
import com.example.a16_room.ui.view.history.AttendanceActivity
import com.example.a16_room.ui.view.history.HistoryActivity
import com.example.a16_room.ui.view.student.ViewStudentActivity

class SubjectMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubjectMenuBinding

    private var subjectId: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Menu"

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        binding.attendance.setOnClickListener{
            val intent = Intent(this@SubjectMenuActivity, AttendanceActivity::class.java)
            intent.putExtra("subject_id", subjectId)
            startActivity(intent)
        }
        binding.student.setOnClickListener{
            val intent = Intent(this@SubjectMenuActivity, ViewStudentActivity::class.java)
            intent.putExtra("subject_id", subjectId)
            startActivity(intent)
        }

        binding.history.setOnClickListener{
            val intent = Intent(this@SubjectMenuActivity, HistoryActivity::class.java)
            intent.putExtra("subject_id", subjectId)
            startActivity(intent)
        }


    }
}