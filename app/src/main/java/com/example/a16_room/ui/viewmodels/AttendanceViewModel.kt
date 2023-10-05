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

    private val attendance = MutableLiveData<AttendanceModel>()

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
//
//    fun get(studentId: Long) {
//        attendance.value = repository.get(studentId)
//    }
//
    fun update(attendanceId: Long, dateTime: Long, totalStudents: Int, totalPresents: Int): Int {
        val model = AttendanceModel().apply {
            this.attendanceId = attendanceId
            this.dateTime = dateTime
            this.totalStudents = totalStudents
            this.totalPresents = totalPresents
        }
        val affectedRows = repository.update(model)
        changes.value = affectedRows.toLong()
        return affectedRows
    }
//
//    fun delete(attendanceId: Long): Int {
//        val model = AttendanceModel().apply {
//            this.attendanceId = attendanceId
//        }
//        val affectedRows = repository.delete(model)
//        changes.value = affectedRows.toLong()
//        return affectedRows
//    }
//
//    fun getAllAttendancesForStudent(studentId: Long) {
//        listAttendances.value = repository.getAllAttendancesForStudent(studentId)
//    }
}