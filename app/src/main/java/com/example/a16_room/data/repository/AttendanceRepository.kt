package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.AttendanceModel

class AttendanceRepository(context: Context) {
    private val attendanceDAO = AppDatabase.getDatabase(context).attendanceDAO()

    fun insert(attendanceModel: AttendanceModel){
        return attendanceDAO.insert(attendanceModel)
    }
//    fun update(attendance: AttendanceModel): Int {
//        return attendanceDAO.update(attendance)
//    }
//
//    fun delete(attendance: AttendanceModel): Int {
//        return attendanceDAO.delete(attendance)
//    }
//
//    fun get(attendanceId: Long): AttendanceModel {
//        return attendanceDAO.get(attendanceId)
//    }
//
    fun getAll(): List<AttendanceModel> {
        return attendanceDAO.getAll()
    }
//    fun getAllAttendancesForStudent(studentId: Long): List<AttendanceModel> {
//        return attendanceDAO.getAllAttendancesForStudent(studentId)
//    }
}