package com.example.a16_room.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef
import com.example.a16_room.data.repository.StudentAttendanceRepository

class StudentAttendanceViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentAttendanceRepository(application.applicationContext)

    private val listStudents = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> = listStudents

    private val listAttendances = MutableLiveData<List<AttendanceModel>>()
    val attendances: LiveData<List<AttendanceModel>> = listAttendances

    private val listStudentsAttendance = MutableLiveData<List<StudentAttendanceCrossRef>>()
    val studentsAttendance: LiveData<List<StudentAttendanceCrossRef>> = listStudentsAttendance

    private val studentModel = MutableLiveData<StudentModel>()
    var student: LiveData<StudentModel> = studentModel

    private val attendanceModel = MutableLiveData<AttendanceModel>()
    var attendance: LiveData<AttendanceModel> = attendanceModel

    private var changes = MutableLiveData<Long>()
    var newChange: LiveData<Long> = changes

    fun getAllStudentsFromAttendance(attendanceId: Long) {
        listStudents.value = repository.getAllStudentsFromAttendance(attendanceId)
    }

    fun getAllAttendancesFromSubject(subjectId: Long) {
        listAttendances.value = repository.getAllAttendancesFromSubject(subjectId)
    }

    fun getPresences(attendanceId: Long, subjectId: Long) {
        listStudentsAttendance.value = repository.getPresences(attendanceId, subjectId)
    }

    fun insert(attendanceId: Long, subjectId: Long, studentId: Long, isPresent: Boolean) {
        repository.insert(attendanceId, subjectId, studentId, isPresent)
    }
    fun update(attendanceId: Long, subjectId: Long, studentId: Long, isPresent: Boolean) {
        repository.update(attendanceId, subjectId, studentId, isPresent)
    }
}