package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.AttendanceModel

class AttendanceRepository(context: Context) {
    private val attendanceDAO = AppDatabase.getDatabase(context).attendanceDAO()

    fun insert(attendanceModel: AttendanceModel): Long {
        return attendanceDAO.insert(attendanceModel)
    }

    fun update(attendance: AttendanceModel): Int {
        return attendanceDAO.update(attendance)
    }

    fun delete(attendanceId: Long): Int {
        val attendanceModel = getAttendance(attendanceId)
        return attendanceDAO.delete(attendanceModel)
    }

    fun deleteStudentAttendanceCrossRefByAttendanceId(attendanceId: Long) {
        attendanceDAO.deleteStudentAttendanceCrossRefByAttendanceId(attendanceId)
    }

    fun getAll(): List<AttendanceModel> {
        return attendanceDAO.getAll()
    }

    fun getAllAttendancesFromSubject(subjectId: Long): List<AttendanceModel> {
        return attendanceDAO.getAllAttendancesFromSubject(subjectId)
    }

    fun getAttendance(attendanceId: Long): AttendanceModel {
        return attendanceDAO.getAttendance(attendanceId)
    }
}