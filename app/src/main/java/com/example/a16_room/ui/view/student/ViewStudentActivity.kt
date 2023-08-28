package com.example.a16_room.ui.view.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.ui.adapters.StudentAdapter
import com.example.a16_room.ui.listeners.OnStudentListener
import com.example.a16_room.ui.viewmodels.StudentViewModel
import com.example.a16_room.databinding.ActivityMainBinding
import com.example.a16_room.ui.listeners.ClickSourceStudent
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class ViewStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var subjectViewModel: SubjectViewModel
    private val adapter = StudentAdapter()
    private var subjectId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        subjectViewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        viewModel = ViewModelProvider(this)[StudentViewModel::class.java]

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getIntExtra("subject_id", -1)
        }

        binding.recyclerStudents.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerStudents.adapter = adapter

        val Intent = Intent(this, EditStudentActivity::class.java)
        val listener = object : OnStudentListener {
            override fun OnClick(id: Long, source: ClickSourceStudent) {
                when (source) {
                    ClickSourceStudent.TEXT -> {
                        Intent.putExtra("student_id", id)
                        startActivity(Intent)
                    }

                    ClickSourceStudent.BUTTON_REMOVE -> {
                        viewModel.delete(id)
                    }
                }
            }
        }

        binding.buttonNewStudent.setOnClickListener {
            val intent = Intent(this, CreateStudentActivity::class.java)
            intent.putExtra("subject_id", subjectId)
            startActivity(intent)
        }

        adapter.attachListener(listener)

        viewModel.getAllStudentsInSubject(subjectId)
        observe()
    }

    //Garante a atualização da lista quando volta da edição/criação
    override fun onResume() {
        super.onResume()
        viewModel.getAllStudentsInSubject(subjectId)
    }

    private fun observe() {
        viewModel.students.observe(this) {
            adapter.updateStudents(it)
        }
        viewModel.newChange.observe(this) {
            viewModel.getAllStudentsInSubject(subjectId)
        }
    }
}