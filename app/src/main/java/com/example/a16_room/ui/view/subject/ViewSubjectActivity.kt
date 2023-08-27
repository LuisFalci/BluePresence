package com.example.a16_room.ui.view.subject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.R
import com.example.a16_room.databinding.ActivityViewSubjectBinding
import com.example.a16_room.databinding.RowSubjectBinding
import com.example.a16_room.ui.adapters.SubjectAdapter
import com.example.a16_room.ui.listeners.ClickSourceSubject
import com.example.a16_room.ui.listeners.OnSubjectListener
import com.example.a16_room.ui.view.student.MainActivity
import com.example.a16_room.ui.viewmodels.SubjectViewModel

class ViewSubjectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewSubjectBinding
    private lateinit var viewModel: SubjectViewModel
    private val adapter = SubjectAdapter()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSubjectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SubjectViewModel::class.java]

        binding.recyclerSubjects.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerSubjects.adapter = adapter

        val listener = object : OnSubjectListener {
            override fun OnClick(subjectId: Int, source: ClickSourceSubject) {
                when (source) {
                    ClickSourceSubject.OPTION_VIEW_STUDENTS -> {
                        val intent = Intent(this@ViewSubjectActivity, MainActivity::class.java)
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