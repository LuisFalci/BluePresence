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

    fun delete(attendance: AttendanceModel): Int {
        return attendanceDAO.delete(attendance)
    }

    fun getAll(): List<AttendanceModel> {
        return attendanceDAO.getAll()
    }

    fun getAttendance(attendanceId: Long): AttendanceModel {
        return attendanceDAO.getAttendance(attendanceId)
    }
}