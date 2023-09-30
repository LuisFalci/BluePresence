package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.AttendanceModel
import com.example.a16_room.data.models.StudentModel
import com.example.a16_room.data.models.relations.studentattendance.StudentAttendanceCrossRef

class StudentAttendanceRepository(context: Context) {
    private val studentAttendanceDAO = AppDatabase.getDatabase(context).studentAttendance()
    fun insert(attendanceId: Long, subjectId: Long, studentId: Long, isPresent: Boolean) {
        val studentAttendanceCrossRef = StudentAttendanceCrossRef(attendanceId, subjectId, studentId, isPresent)
         studentAttendanceDAO.insert(studentAttendanceCrossRef)
    }
    fun update(studentAttendanceCrossRef: StudentAttendanceCrossRef): Int{
        return studentAttendanceDAO.update(studentAttendanceCrossRef)
    }

    fun delete(studentAttendanceCrossRef: StudentAttendanceCrossRef): Int{
        return studentAttendanceDAO.delete(studentAttendanceCrossRef)
    }

    fun getAllStudentsFromAttendance(attendanceId: Long): List<StudentModel>{
        return studentAttendanceDAO.getAllStudentsFromAttendance(attendanceId)
    }

    fun getAllAttendancesFromSubject(subjectId: Long): List<AttendanceModel>{
        return studentAttendanceDAO.getAllAttendancesFromSubject(subjectId)
    }
}