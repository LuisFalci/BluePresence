package com.example.a16_room.ui.view.subject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.databinding.ActivityViewSubjectBinding
import com.example.a16_room.ui.adapters.SubjectAdapter
import com.example.a16_room.ui.listeners.ClickSourceSubject
import com.example.a16_room.ui.listeners.OnSubjectListener
import com.example.a16_room.ui.view.SubjectMenuActivity
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class ViewSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewSubjectBinding
    private lateinit var viewModel: SubjectViewModel
    private val adapter = SubjectAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var action = supportActionBar
        action!!.title = "Turmas"

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.recyclerSubjects.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerSubjects.adapter = adapter

        val listener = object : OnSubjectListener {
            override fun OnClick(subjectId: Long, source: ClickSourceSubject) {
                when (source) {
                    ClickSourceSubject.OPTION_VIEW_STUDENTS -> {
//                        val intent = Intent(this@ViewSubjectActivity, ViewStudentActivity::class.java)
                        val intent = Intent(this@ViewSubjectActivity, SubjectMenuActivity::class.java)
                        intent.putExtra("subject_id", subjectId)
                        startActivity(intent)
                    }
                    ClickSourceSubject.OPTION_EDIT -> {
                        val intent = Intent(this@ViewSubjectActivity, EditSubjectActivity::class.java)
                        intent.putExtra("subject_id", subjectId)
                        startActivity(intent)
                    }
                    ClickSourceSubject.OPTION_REMOVE -> {
                        // Handle delete action directly here
                        viewModel.delete(subjectId)
                    }
                }
            }
        }

        binding.buttonNewSubject.setOnClickListener {
            startActivity(Intent(this, CreateSubjectActivity::class.java))
        }

        adapter.attachListener(listener)

        viewModel.getAll()
        observe()

    }
    override fun onResume() {
        super.onResume()
        viewModel.getAll()
    }

    private fun observe() {
        viewModel.subjects.observe(this) {
            adapter.updateSubjects(it)
        }
        viewModel.newChange.observe(this) {
            viewModel.getAll()
        }
    }
}