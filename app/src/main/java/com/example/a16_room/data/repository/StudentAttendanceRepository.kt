package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef
import com.example.a16_room.data.models.utils.StudentAttendanceInfo

class StudentAttendanceRepository(context: Context) {
    private val studentAttendanceDAO = AppDatabase.getDatabase(context).studentAttendance()
    fun insert(attendanceId: Long, subjectId: Long, studentId: Long, isPresent: Boolean) {
        val studentAttendanceCrossRef =
            StudentAttendanceCrossRef(attendanceId, subjectId, studentId, isPresent)
        studentAttendanceDAO.insert(studentAttendanceCrossRef)
    }

    fun update(attendanceId: Long, subjectId: Long, studentId: Long, isPresent: Boolean): Int {
        val studentAttendanceCrossRef =
            StudentAttendanceCrossRef(attendanceId, subjectId, studentId, isPresent)
        return studentAttendanceDAO.update(studentAttendanceCrossRef)
    }

    fun getAllStudentsFromAttendance(attendanceId: Long): List<StudentModel> {
        return studentAttendanceDAO.getAllStudentsFromAttendance(attendanceId)
    }

    fun getPresences(attendanceId: Long, subjectId: Long): List<StudentAttendanceCrossRef> {
        return studentAttendanceDAO.getPresences(attendanceId, subjectId)
    }
    fun getStudentAttendanceInfo(subjectId: Long): List<StudentAttendanceInfo>{
        return studentAttendanceDAO.getStudentAttendanceInfo(subjectId)
    }
}