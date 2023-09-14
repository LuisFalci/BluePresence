package com.example.a16_room.data.repository

import android.content.Context
import com.example.a16_room.data.database.AppDatabase
import com.example.a16_room.data.models.AttendanceModel

class AttendanceRepository(context: Context) {
    private val attendanceDAO = AppDatabase.getDatabase(context).attendanceDAO()

    fun insert(attendanceModel: AttendanceModel){
        return attendanceDAO.insert(attendanceModel)
    }
}