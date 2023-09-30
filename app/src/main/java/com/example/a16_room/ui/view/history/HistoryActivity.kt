package com.example.a16_room.ui.view.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.databinding.ActivityHistoryBinding
import com.example.a16_room.ui.adapters.HistoryAdapter
import com.example.a16_room.ui.viewmodels.StudentAttendanceViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var studentAttendanceViewModel: StudentAttendanceViewModel
    private val adapter = HistoryAdapter()

    private var subjectId: Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var action = supportActionBar
        action!!.title = "Histórico de Chamadas"

        if (intent.hasExtra("subject_id")) {
            subjectId = intent.getLongExtra("subject_id", -1L)
        }

        studentAttendanceViewModel = ViewModelProvider(this)[StudentAttendanceViewModel::class.java]

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.historyRecyclerView.adapter = adapter

        studentAttendanceViewModel.getAllAttendancesFromSubject(subjectId)
        observe()
    }
    private fun observe() {
        studentAttendanceViewModel.attendances.observe(this) {
            adapter.updateHistory(it)
        }
        studentAttendanceViewModel.newChange.observe(this) {
            studentAttendanceViewModel.getAllAttendancesFromSubject(subjectId)
        }
    }
}