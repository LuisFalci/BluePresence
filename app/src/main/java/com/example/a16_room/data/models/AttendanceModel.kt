package com.example.a16_room.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
class AttendanceModel() {
    @PrimaryKey(autoGenerate = true)
    var attendanceId: Long = 0
    var dateTime: Long = 0
}