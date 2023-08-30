package com.example.a16_room.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Attendance")
class AttendanceModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var attendanceId: Long = 0

    @ColumnInfo(name = "attendance")
    var presence: Boolean = false

    @ColumnInfo(name = "attendanceTimestamp")
    var attendanceTimestamp: String = ""
}
