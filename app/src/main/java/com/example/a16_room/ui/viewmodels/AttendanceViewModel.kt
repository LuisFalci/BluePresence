package com.example.a16_room.ui.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.repository.AttendanceRepository

class AttendanceViewModel(application: Application): AndroidViewModel(application) {
    private val repository = AttendanceRepository(application.applicationContext)

    private val listAttendances = MutableLiveData<List<AttendanceModel>>()

    private val attendance = MutableLiveData<AttendanceModel>()

    private var changes = MutableLiveData<Long>()

    fun getAll() {
        listAttendances.value = repository.getAll()
    }

    fun get(studentId: Long) {
        attendance.value = repository.get(studentId)
    }

    fun insert(studentId: Long, attendanceTimestamp: String, presence: Boolean): Long {
        val model = AttendanceModel().apply {
            this.id = studentId
            this.attendanceTimestamp = attendanceTimestamp
            this.presence = presence
        }
        return repository.insert(model)
    }

    fun update(attendanceId: Long,studentId: Long, attendanceTimestamp: String, presence: Boolean): Int {
        val model = AttendanceModel().apply {
            this.attendanceId = attendanceId
            this.id = studentId
            this.attendanceTimestamp = attendanceTimestamp
            this.presence = presence
        }
        val affectedRows = repository.update(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }

    fun delete(attendanceId: Long): Int {
        val model = AttendanceModel().apply {
            this.attendanceId = attendanceId
        }
        val affectedRows = repository.delete(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }

    fun getAllAttendancesForStudent(studentId: Long) {
        listAttendances.value = repository.getAllAttendancesForStudent(studentId)
    }
}