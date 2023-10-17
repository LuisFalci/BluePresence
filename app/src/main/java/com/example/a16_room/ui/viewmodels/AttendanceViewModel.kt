package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.repository.AttendanceRepository

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AttendanceRepository(application.applicationContext)

    private val listAttendances = MutableLiveData<List<AttendanceModel>>()
    val attendances: LiveData<List<AttendanceModel>> = listAttendances

    private val attendanceModel = MutableLiveData<AttendanceModel>()
    var attendance: LiveData<AttendanceModel> = attendanceModel

    private var changes = MutableLiveData<Long>()
    var newChange: LiveData<Long> = changes

    fun insert(subjectId: Long, dateTime: Long, totalStudents: Int, totalPresents: Int): Long {
        val model = AttendanceModel().apply {
            this.subjectId = subjectId
            this.dateTime = dateTime
            this.totalStudents = totalStudents
            this.totalPresents = totalPresents
        }
        return repository.insert(model)
    }

    fun getAll() {
        listAttendances.value = repository.getAll()
    }

    fun getAttendance(attendanceId: Long) {
        attendanceModel.value = repository.getAttendance(attendanceId)
    }

    fun getAllAttendancesFromSubject(subjectId: Long) {
        listAttendances.value = repository.getAllAttendancesFromSubject(subjectId)
    }

    fun update(
        attendanceId: Long,
        subjectId: Long,
        dateTime: Long,
        totalStudents: Int,
        totalPresents: Int
    ): Int {
        val model = AttendanceModel().apply {
            this.attendanceId = attendanceId
            this.subjectId = subjectId
            this.dateTime = dateTime
            this.totalStudents = totalStudents
            this.totalPresents = totalPresents
        }
        val affectedRows = repository.update(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }

    fun deleteStudentAttendanceCrossRefByAttendanceId(attendanceId: Long) {
        repository.deleteStudentAttendanceCrossRefByAttendanceId(attendanceId)
    }
    fun delete(attendanceId: Long): Int {
        val affectedRows = repository.delete(attendanceId)
        changes.value = affectedRows.toLong()
        return affectedRows
    }
}