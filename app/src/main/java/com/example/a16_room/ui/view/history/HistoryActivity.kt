package com.example.a16_room.ui.view.history

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a16_room.databinding.ActivityHistoryBinding
import com.example.a16_room.ui.adapters.HistoryAdapter
import com.example.a16_room.ui.listeners.OnHistoryListener
import com.example.a16_room.ui.viewmodels.AttendanceViewModel
import com.example.a16_room.ui.viewmodels.StudentAttendanceViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var studentAttendanceViewModel: StudentAttendanceViewModel
    private lateinit var attendanceViewModel: AttendanceViewModel

    private val adapter = HistoryAdapter()
    private val EDIT_HISTORY_REQUEST_CODE = 1

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
        attendanceViewModel = ViewModelProvider(this)[AttendanceViewModel::class.java]

        binding.historyRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        binding.historyRecyclerView.adapter = adapter

        val listener = object : OnHistoryListener {
            override fun onHistoryClick(attendanceId: Long) {
                val intent = Intent(this@HistoryActivity, EditHistoryActivity::class.java)
                intent.putExtra("attendanceId", attendanceId)
                intent.putExtra("subjectId", subjectId)
                startActivityForResult(intent, EDIT_HISTORY_REQUEST_CODE)
            }

            override fun onRemoveClick(attendanceId: Long) {
                attendanceViewModel.deleteStudentAttendanceCrossRefByAttendanceId(attendanceId)
                attendanceViewModel.delete(attendanceId)
            }
        }
        adapter.attachListener(listener)

        attendanceViewModel.getAllAttendancesFromSubject(subjectId)
        observe()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_HISTORY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Atualize a lista de histórico após a edição
            attendanceViewModel.getAllAttendancesFromSubject(subjectId)
        }
    }

    private fun observe() {
        attendanceViewModel.attendances.observe(this) {
            adapter.updateHistory(it)
        }
        attendanceViewModel.newChange.observe(this) {
            attendanceViewModel.getAllAttendancesFromSubject(subjectId)
        }
    }
}