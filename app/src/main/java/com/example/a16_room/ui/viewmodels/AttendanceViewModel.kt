package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.repository.AttendanceRepository

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AttendanceRepository(application.applicationContext)

    fun insert(studentId: Long, presence: Boolean, dateTime: String) {
        val model = AttendanceModel().apply {
            this.student_id = studentId
            this.presence = presence
        }
        repository.insert(model)
    }
}